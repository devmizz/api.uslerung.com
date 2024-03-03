package com.scheduleapplication.vo

import com.scheduledomain.vo.MeetingLocation

enum class MeetingLocationInApplication {
    NO_WINDOW_CONFERENCE_ROOM,
    WINDOW_CONFERENCE_ROOM,
    OUTSIDE;

    companion object {

        fun from(meetingLocation: MeetingLocation) = when (meetingLocation) {
            MeetingLocation.NO_WINDOW_CONFERENCE_ROOM -> NO_WINDOW_CONFERENCE_ROOM
            MeetingLocation.WINDOW_CONFERENCE_ROOM -> WINDOW_CONFERENCE_ROOM
            MeetingLocation.OUTSIDE -> OUTSIDE
        }
    }

    fun toDomainLayer() = when (this) {
        NO_WINDOW_CONFERENCE_ROOM -> MeetingLocation.NO_WINDOW_CONFERENCE_ROOM
        WINDOW_CONFERENCE_ROOM -> MeetingLocation.WINDOW_CONFERENCE_ROOM
        OUTSIDE -> MeetingLocation.OUTSIDE
    }
}
