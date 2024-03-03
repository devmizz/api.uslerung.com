package com.scheduleapplication.dto.response

import com.scheduleapplication.vo.MeetingLocationInApplication
import com.scheduleapplication.vo.MeetingTypeInApplication
import com.scheduledomain.entity.Meeting
import com.userdomain.entity.User
import java.time.LocalDateTime

data class SimpleMeetingApplicationResponse(
    val beginAt: LocalDateTime,
    val endAt: LocalDateTime,
    val host: MeetingHostApplicationResponse,
    val title: String,
    val meetingLocation: MeetingLocationInApplication,
    val meetingType: MeetingTypeInApplication
) {

    companion object {

        fun from(
            meetingResponse: Meeting,
            user: User?
        ) = SimpleMeetingApplicationResponse(
            beginAt = meetingResponse.beginAt,
            endAt = meetingResponse.endAt,
            host = MeetingHostApplicationResponse.from(user),
            title = meetingResponse.title,
            meetingLocation = MeetingLocationInApplication.from(meetingResponse.meetingLocation),
            meetingType = MeetingTypeInApplication.from(meetingResponse.meetingType)
        )
    }
}
