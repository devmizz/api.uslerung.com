package com.client.schedule.vo

import com.scheduleapplication.vo.MeetingLocationInApplication

enum class MeetingLocationInPresentation {
    NO_WINDOW_CONFERENCE_ROOM,
    WINDOW_CONFERENCE_ROOM,
    OUTSIDE;

    companion object {

        fun from(location: MeetingLocationInApplication) = when (location) {
            MeetingLocationInApplication.NO_WINDOW_CONFERENCE_ROOM -> NO_WINDOW_CONFERENCE_ROOM
            MeetingLocationInApplication.WINDOW_CONFERENCE_ROOM -> WINDOW_CONFERENCE_ROOM
            MeetingLocationInApplication.OUTSIDE -> OUTSIDE
        }
    }

    fun toApplicationLayer() = when (this) {
        NO_WINDOW_CONFERENCE_ROOM -> MeetingLocationInApplication.NO_WINDOW_CONFERENCE_ROOM
        WINDOW_CONFERENCE_ROOM -> MeetingLocationInApplication.WINDOW_CONFERENCE_ROOM
        OUTSIDE -> MeetingLocationInApplication.OUTSIDE
    }
}
