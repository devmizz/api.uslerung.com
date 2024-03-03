package com.client.schedule.vo

import com.scheduleapplication.vo.MeetingAttendDecisionStatusInApplication

enum class MeetingAttendanceDecisionStatusInPresentation {
    PENDING,
    ATTEND,
    REFUSED;

    companion object {
        fun from(status: MeetingAttendDecisionStatusInApplication) = when (status) {
            MeetingAttendDecisionStatusInApplication.PENDING -> PENDING
            MeetingAttendDecisionStatusInApplication.CONFIRMED -> ATTEND
            MeetingAttendDecisionStatusInApplication.REFUSED -> REFUSED
        }
    }
}
