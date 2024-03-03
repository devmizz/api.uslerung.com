package com.client.security.dto

data class Token(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(accessToken: String, refreshToken: String) = Token("Bearer", accessToken, refreshToken)
    }
}
