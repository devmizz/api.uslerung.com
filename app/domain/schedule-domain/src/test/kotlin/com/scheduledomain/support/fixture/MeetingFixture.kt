package com.scheduledomain.support.fixture

import com.common.randomUUID
import com.scheduledomain.entity.Meeting
import com.scheduledomain.vo.MeetingLocation
import com.scheduledomain.vo.MeetingType
import java.time.LocalDateTime
import java.util.UUID

fun createMeetingFixture(
    creatorId: UUID = randomUUID(),
    beginAt: LocalDateTime = LocalDateTime.of(2023, 12, 25, 0, 0, 0),
    endAt: LocalDateTime = LocalDateTime.of(2023, 12, 26, 0, 0, 0),
    hostId: UUID = randomUUID(),
    title: String = "title",
    description: String = "description",
    meetingLocation: MeetingLocation = MeetingLocation.NO_WINDOW_CONFERENCE_ROOM,
    outsideLocation: String? = null,
    meetingType: MeetingType = MeetingType.NORMAL
) = Meeting(
    creatorId = creatorId,
    beginAt = beginAt,
    endAt = endAt,
    hostId = hostId,
    title = title,
    description = description,
    meetingLocation = meetingLocation,
    outsideLocation = outsideLocation,
    meetingType = meetingType
)
