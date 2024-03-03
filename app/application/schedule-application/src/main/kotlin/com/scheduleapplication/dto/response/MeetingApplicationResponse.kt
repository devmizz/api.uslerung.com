package com.scheduleapplication.dto.response

import com.scheduleapplication.vo.MeetingLocationInApplication
import com.scheduleapplication.vo.MeetingTypeInApplication
import com.scheduledomain.entity.Meeting
import com.userdomain.entity.User
import global.dto.ApplicationLayerResponseDTO
import java.time.LocalDateTime
import java.util.UUID

data class MeetingApplicationResponse(
    val id: String,
    val creator: MeetingCreatorApplicationResponse,
    val host: MeetingHostApplicationResponse,
    val attendees: List<MeetingAttendeeApplicationResponse>,
    val beginAt: LocalDateTime,
    val endAt: LocalDateTime,
    val title: String,
    val description: String,
    val meetingLocation: MeetingLocationInApplication,
    val outsideLocation: String?,
    val meetingType: MeetingTypeInApplication
) : ApplicationLayerResponseDTO {

    companion object {

        /**
         * @param userById creator, host, invitees를 포함한 모든 유저
         */
        fun from(
            meeting: Meeting,
            userById: Map<UUID, User>
        ): MeetingApplicationResponse {
            val creator = MeetingCreatorApplicationResponse.from(userById[meeting.creatorId])
            val host = MeetingHostApplicationResponse.from(userById[meeting.hostId])

            return MeetingApplicationResponse(
                id = meeting.id.toString(),
                creator = creator,
                host = host,
                attendees = meeting.invitees.map { attendee ->
                    MeetingAttendeeApplicationResponse.from(
                        invitee = attendee,
                        user = userById[attendee.userId]
                    )
                },
                beginAt = meeting.beginAt,
                endAt = meeting.endAt,
                title = meeting.title,
                description = meeting.description,
                meetingLocation = MeetingLocationInApplication.valueOf(meeting.meetingLocation.name),
                outsideLocation = meeting.outsideLocation,
                meetingType = MeetingTypeInApplication.valueOf(meeting.meetingType.name)
            )
        }
    }
}
