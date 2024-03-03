package com.client.schedule.dto.response

import com.scheduleapplication.dto.response.MeetingHostApplicationResponse

data class MeetingHostPresentationResponse(
    val id: String,
    val name: String
) {
    companion object {
        fun from(response: MeetingHostApplicationResponse) = MeetingHostPresentationResponse(
            id = response.id,
            name = response.name
        )
    }
}
