package com.client.schedule.dto.response

import com.client.schedule.vo.MeetingAttendanceDecisionStatusInPresentation
import com.scheduleapplication.dto.response.MeetingAttendeeApplicationResponse

data class MeetingAttendeePresentationResponse(
    val id: String,
    val name: String,
    val attendanceDecisionStatus: MeetingAttendanceDecisionStatusInPresentation
) {
    companion object {
        fun from(response: MeetingAttendeeApplicationResponse) = MeetingAttendeePresentationResponse(
            id = response.id,
            name = response.name,
            attendanceDecisionStatus = MeetingAttendanceDecisionStatusInPresentation.from(
                response.attendanceDecisionStatus
            )
        )
    }
}
