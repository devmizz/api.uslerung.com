package com.client.schedule.dto.response

import com.client.schedule.vo.MeetingLocationInPresentation
import com.client.schedule.vo.MeetingTypeInPresentation
import com.scheduleapplication.dto.response.MeetingApplicationResponse
import java.time.LocalDateTime

data class MeetingPresentationResponse(
    val id: String,
    val creator: MeetingCreatorPresentationResponse,
    val host: MeetingHostPresentationResponse,
    val invitees: List<MeetingAttendeePresentationResponse>,
    val beginAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val description: String,
    val meetingLocation: MeetingLocationInPresentation,
    val outsideLocation: String?,
    val meetingType: MeetingTypeInPresentation
) {

    companion object {
        fun from(response: MeetingApplicationResponse): MeetingPresentationResponse {
            val invitees = response.attendees.map {
                MeetingAttendeePresentationResponse.from(it)
            }

            return MeetingPresentationResponse(
                id = response.id,
                creator = MeetingCreatorPresentationResponse.from(response.creator),
                host = MeetingHostPresentationResponse.from(response.host),
                invitees = invitees,
                beginAt = response.beginAt,
                endAt = response.endAt,
                title = response.title,
                description = response.description,
                meetingLocation = MeetingLocationInPresentation.from(response.meetingLocation),
                outsideLocation = response.outsideLocation,
                meetingType = MeetingTypeInPresentation.from(response.meetingType)
            )
        }
    }
}
