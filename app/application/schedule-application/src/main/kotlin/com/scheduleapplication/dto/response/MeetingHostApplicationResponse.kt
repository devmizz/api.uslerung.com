package com.scheduleapplication.dto.response

import com.userdomain.entity.User

data class MeetingHostApplicationResponse(
    val id: String,
    val name: String
) {
    companion object {
        fun from(
            user: User?
        ): MeetingHostApplicationResponse {
            return user?.let {
                MeetingHostApplicationResponse(
                    id = it.id.toString(),
                    name = user.slackUser.realName
                )
            } ?: MeetingHostApplicationResponse(
                id = "연동 해제 유저",
                name = "연동 해제 유저"
            )
        }
    }
}
