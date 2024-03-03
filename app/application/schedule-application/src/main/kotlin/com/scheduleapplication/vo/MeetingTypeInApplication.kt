package com.scheduleapplication.vo

import com.scheduledomain.vo.MeetingType

enum class MeetingTypeInApplication {
    NORMAL,
    REGULAR,
    EMERGENCY;

    companion object {
        fun from(meetingType: MeetingType) = when (meetingType) {
            MeetingType.NORMAL -> NORMAL
            MeetingType.REGULAR -> REGULAR
            MeetingType.EMERGENCY -> EMERGENCY
        }
    }

    fun toDomainLayer() = when (this) {
        NORMAL -> MeetingType.NORMAL
        REGULAR -> MeetingType.REGULAR
        EMERGENCY -> MeetingType.EMERGENCY
    }
}
