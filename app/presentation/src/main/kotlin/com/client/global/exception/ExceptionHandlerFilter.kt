package com.client.global.exception

import com.common.error.CustomException
import com.common.randomStringUUID
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private val log = KotlinLogging.logger {}

@Component
class ExceptionHandlerFilter : OncePerRequestFilter() {

    /**
     * ControllerAdvice에서 처리되지 않는 CustomException을 처리
     * Filter, Interceptor에서 발생하는 CustomException을 처리
     */
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: CustomException) {
            val errorId = randomStringUUID()

            log.error(errorLogFormat(errorId, e.error.description), e)

            response.status = e.error.customHttpStatus.httpStatusCode
            response.contentType = "application/json; charset=UTF-8"

            response.writer.write(
                ObjectMapper().writeValueAsString(ExceptionResponse.of(errorId, e.error.description))
            )
        }
    }
}
