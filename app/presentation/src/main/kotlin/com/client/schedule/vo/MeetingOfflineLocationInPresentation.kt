package com.client.schedule.vo

import com.scheduleapplication.vo.MeetingLocationInApplication

enum class MeetingOfflineLocationInPresentation {
    NO_WINDOW_CONFERENCE_ROOM,
    WINDOW_CONFERENCE_ROOM;

    fun toApplicationLayer() = when (this) {
        NO_WINDOW_CONFERENCE_ROOM -> MeetingLocationInApplication.NO_WINDOW_CONFERENCE_ROOM
        WINDOW_CONFERENCE_ROOM -> MeetingLocationInApplication.WINDOW_CONFERENCE_ROOM
    }
}
