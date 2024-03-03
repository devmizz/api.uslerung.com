package com.scheduleapplication.dto.response

import com.userdomain.entity.User

data class MeetingCreatorApplicationResponse(
    val id: String,
    val name: String
) {
    companion object {
        fun from(response: User?): MeetingCreatorApplicationResponse {
            return response?.let {
                MeetingCreatorApplicationResponse(
                    id = it.id.toString(),
                    name = it.slackUser.realName
                )
            } ?: MeetingCreatorApplicationResponse(
                id = "연동 해제 유저",
                name = "연동 해제 유저"
            )
        }
    }
}
