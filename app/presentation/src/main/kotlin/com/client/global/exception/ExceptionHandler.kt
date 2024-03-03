package com.client.global.exception

import com.common.error.CustomException
import com.common.randomStringUUID
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(value = [CustomException::class])
    fun handleCustomException(e: CustomException): ResponseEntity<ExceptionResponse> {
        val errorId = randomStringUUID()

        log.error(errorLogFormat(errorId, e.error.description), e)

        return ResponseEntity.status(HttpStatusCode.valueOf(e.error.customHttpStatus.httpStatusCode))
            .body(ExceptionResponse.of(errorId, e.error.description))
    }

    @ExceptionHandler(value = [RuntimeException::class])
    fun handleRuntimeException(e: RuntimeException): ResponseEntity<ExceptionResponse> {
        val errorId = randomStringUUID()
        val message = "서버 내부에서 처리되지 않은 에러입니다."

        log.error(errorLogFormat(errorId, message), e)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ExceptionResponse.of(errorId, message))
    }
}
