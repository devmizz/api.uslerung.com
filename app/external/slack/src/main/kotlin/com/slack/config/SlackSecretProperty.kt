package com.slack.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "slack.secret")
data class SlackSecretProperty(
    val botToken: String,
    val appId: String,
    val clientId: String,
    val clientSecret: String,
    val signingSecret: String,
    val verificationToken: String
)
