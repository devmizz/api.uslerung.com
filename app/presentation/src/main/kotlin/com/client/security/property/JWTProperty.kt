package com.client.security.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JWTProperty(
    val secretKey: String,
    val accessTokenExpirationSeconds: String,
    val refreshTokenExpirationSeconds: String
)
