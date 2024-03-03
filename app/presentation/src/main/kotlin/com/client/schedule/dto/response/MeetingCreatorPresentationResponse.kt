package com.client.schedule.dto.response

import com.scheduleapplication.dto.response.MeetingCreatorApplicationResponse

data class MeetingCreatorPresentationResponse(
    val id: String,
    val name: String
) {
    companion object {
        fun from(response: MeetingCreatorApplicationResponse): MeetingCreatorPresentationResponse {
            return MeetingCreatorPresentationResponse(
                id = response.id,
                name = response.name
            )
        }
    }
}
