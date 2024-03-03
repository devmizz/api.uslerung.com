package com.scheduleapplication.dto.response

import com.scheduleapplication.vo.MeetingAttendDecisionStatusInApplication
import com.scheduledomain.entity.Invitee
import com.userdomain.entity.User

data class MeetingAttendeeApplicationResponse(
    val id: String,
    val name: String,
    val attendanceDecisionStatus: MeetingAttendDecisionStatusInApplication
) {
    companion object {

        fun from(
            invitee: Invitee,
            user: User?
        ): MeetingAttendeeApplicationResponse = user?.let {
            MeetingAttendeeApplicationResponse(
                id = user.id.toString(),
                name = user.slackUser.realName,
                attendanceDecisionStatus = MeetingAttendDecisionStatusInApplication.from(
                    invitee.attendanceDecisionStatus
                )
            )
        } ?: MeetingAttendeeApplicationResponse(
            id = "연동 해제 유저입니다.",
            name = "연동 해제 유저입니다.",
            attendanceDecisionStatus = MeetingAttendDecisionStatusInApplication.from(invitee.attendanceDecisionStatus)
        )
    }
}
