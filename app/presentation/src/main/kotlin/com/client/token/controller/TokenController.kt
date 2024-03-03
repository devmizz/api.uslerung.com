package com.client.token.controller

import com.client.security.jwt.JWTProvider
import com.client.token.dto.response.TokenPresentationResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "토큰")
@RestController
@RequestMapping("/v1/tokens")
class TokenController(
    private val jwtProvider: JWTProvider
) {

    @Tag(name = "토큰")
    @Operation(summary = "토큰 발급")
    @PostMapping
    fun issueToken(
        @RequestParam userId: String
    ): ResponseEntity<TokenPresentationResponse> {
        return ResponseEntity.ok(
            TokenPresentationResponse.of(jwtProvider.createToken(userId))
        )
    }
}
