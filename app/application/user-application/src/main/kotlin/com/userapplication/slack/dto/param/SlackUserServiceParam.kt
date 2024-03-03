package com.userapplication.slack.dto.param

import com.userdomain.dto.param.SlackUserDomainParam

data class SlackUserServiceParam(
    val slackId: String,
    val teamId: String,
    val displayName: String,
    val realName: String,
    val email: String,
    val imageOrigin: String
) {
    fun toDomainParam() = SlackUserDomainParam(
        slackId = this.slackId,
        teamId = this.teamId,
        displayName = this.displayName,
        realName = this.realName,
        email = this.email,
        imageOrigin = this.imageOrigin
    )
}
