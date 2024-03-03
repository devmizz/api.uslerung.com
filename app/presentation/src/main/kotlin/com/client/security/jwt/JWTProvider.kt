package com.client.security.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.client.security.dto.Token
import com.client.security.property.JWTProperty
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JWTProvider(
    private val jwtProperty: JWTProperty
) {

    fun createToken(userId: String) = Token.of(createAccessToken(userId), createRefreshToken())

    private fun createAccessToken(userId: String): String {
        val now = Date().time
        return JWT.create().withSubject("AccessToken")
            .withClaim("claim", mapOf("userId" to userId))
            .withExpiresAt(Date(now + jwtProperty.accessTokenExpirationSeconds.toLong()))
            .sign(Algorithm.HMAC512(jwtProperty.secretKey))
    }

    private fun createRefreshToken(): String {
        val now = Date().time
        return JWT.create()
            .withSubject("RefreshToken")
            .withExpiresAt(Date(now + jwtProperty.refreshTokenExpirationSeconds.toLong()))
            .sign(Algorithm.HMAC512(jwtProperty.secretKey))
    }
}
