package com.userdomain.dto.param

data class SlackUserDomainParam(
    val slackId: String,
    val teamId: String,
    val displayName: String,
    val realName: String,
    val email: String,
    val imageOrigin: String
)
