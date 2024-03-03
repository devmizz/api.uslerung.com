package com.userapplication.user.dto.param

import com.userdomain.entity.User

data class UserInvitationApplicationParam(
    val hostName: String,
    val guestId: String,
    val guestSlackId: String
) {

    companion object {

        fun from(
            host: User,
            guest: User
        ): UserInvitationApplicationParam = UserInvitationApplicationParam(
            hostName = host.slackUser.realName,
            guestId = guest.id.toString(),
            guestSlackId = guest.slackUser.slackId
        )
    }
}
