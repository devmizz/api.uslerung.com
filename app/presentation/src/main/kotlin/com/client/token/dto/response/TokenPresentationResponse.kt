package com.client.token.dto.response

import com.client.security.dto.Token

data class TokenPresentationResponse(
    val accessToken: String,
    val refreshToken: String
) {

    companion object {

        fun of(token: Token) = TokenPresentationResponse(
            token.accessToken,
            token.refreshToken
        )
    }
}
