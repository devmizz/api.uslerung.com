package com.client.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.client.security.dto.SecurityUser
import com.client.security.error.JWTError
import com.client.security.property.JWTProperty
import com.common.error.CustomException
import com.common.isNullThen
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private val log = KotlinLogging.logger {}

@Component
class JWTFilter(
    private val jwtProperty: JWTProperty
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = request.getHeader("Authorization")
        authorization.isNullThen {
            filterChain.doFilter(request, response)
            return
        }

        val extractedToken = extractToken(request).also { validateToken(it) }
        extractClaim(extractedToken).also { saveOnSecurityContextHolder(it) }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: ServletRequest): String {
        val httpRequest = request as HttpServletRequest
        val token = httpRequest.getHeader("Authorization")

        if (token.isNotEmpty() && token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "")
        }

        throw CustomException(JWTError.INVALID_TOKEN)
    }

    private fun validateToken(token: String) {
        try {
            JWT.require(Algorithm.HMAC512(jwtProperty.secretKey))
                .build()
                .verify(token)
        } catch (e: TokenExpiredException) {
            throw CustomException(JWTError.EXPIRED_TOKEN)
        } catch (e: NullPointerException) {
            throw CustomException(JWTError.NEED_TOKEN)
        } catch (e: Exception) {
            throw CustomException(JWTError.INVALID_TOKEN)
        }
    }

    private fun extractClaim(token: String): Map<String, Any> {
        return JWT.require(Algorithm.HMAC512(jwtProperty.secretKey))
            .build()
            .verify(token)
            .getClaim("claim")
            .asMap()
    }

    private fun saveOnSecurityContextHolder(claim: Map<String, Any>) {
        val user = SecurityUser(claim["userId"].toString())

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(user, null, null)
    }
}
