package com.client.schedule.vo

import com.scheduleapplication.vo.MeetingTypeInApplication

enum class MeetingTypeInPresentation {
    NORMAL,
    REGULAR,
    EMERGENCY;

    companion object {

        fun from(type: MeetingTypeInApplication) = when (type) {
            MeetingTypeInApplication.NORMAL -> NORMAL
            MeetingTypeInApplication.REGULAR -> REGULAR
            MeetingTypeInApplication.EMERGENCY -> EMERGENCY
        }
    }

    fun toApplicationLayer() = when (this) {
        NORMAL -> MeetingTypeInApplication.NORMAL
        REGULAR -> MeetingTypeInApplication.REGULAR
        EMERGENCY -> MeetingTypeInApplication.EMERGENCY
    }
}
