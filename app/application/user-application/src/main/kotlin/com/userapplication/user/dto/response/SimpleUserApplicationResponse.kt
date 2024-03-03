package com.userapplication.user.dto.response

import com.userdomain.entity.User
import java.util.UUID

data class SimpleUserApplicationResponse(
    val id: UUID,
    val name: String,
    val hasSlackLinkApproval: Boolean
) {

    companion object {

        fun from(user: User) = SimpleUserApplicationResponse(
            id = user.id,
            name = user.slackUser.realName,
            hasSlackLinkApproval = user.hasSlackLinkApproval
        )
    }
}
