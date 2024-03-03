package com.scheduleapplication.vo

import com.scheduledomain.vo.AttendanceDecisionStatus

enum class MeetingAttendDecisionStatusInApplication {
    PENDING,
    CONFIRMED,
    REFUSED;

    companion object {

        fun from(status: AttendanceDecisionStatus): MeetingAttendDecisionStatusInApplication = when (status) {
            AttendanceDecisionStatus.PENDING -> PENDING
            AttendanceDecisionStatus.CONFIRMED -> CONFIRMED
            AttendanceDecisionStatus.REFUSED -> REFUSED
        }
    }
}
