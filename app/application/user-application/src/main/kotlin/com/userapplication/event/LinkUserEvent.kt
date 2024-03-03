package com.userapplication.event

import com.userdomain.entity.User

data class LinkUserEvent(
    val userSlackId: String
) {

    companion object {

        fun from(user: User): LinkUserEvent = LinkUserEvent(
            userSlackId = user.slackUser.slackId
        )
    }
}
