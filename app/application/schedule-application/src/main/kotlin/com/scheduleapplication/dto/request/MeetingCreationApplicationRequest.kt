package com.scheduleapplication.dto.request

import com.common.uuidFrom
import com.scheduleapplication.vo.MeetingLocationInApplication
import com.scheduleapplication.vo.MeetingTypeInApplication
import com.scheduledomain.entity.Invitee
import com.scheduledomain.entity.Meeting
import global.dto.ApplicationLayerRequestDTO
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.UUID

data class MeetingCreationApplicationRequest(
    val meetingType: MeetingTypeInApplication,

    val title: String,

    val description: String,

    val date: LocalDate,

    val beginAt: LocalTime,

    val endAt: LocalTime,

    val location: MeetingLocationInApplication,

    val outsideLocation: String? = null,

    val inviteeIds: List<String>
) : ApplicationLayerRequestDTO {

    fun toMeeting(creatorId: String): Meeting = Meeting(
        creatorId = UUID.fromString(creatorId),
        beginAt = LocalDateTime.of(date, beginAt),
        endAt = LocalDateTime.of(date, endAt),
        hostId = UUID.fromString(creatorId),
        title = title,
        description = description,
        meetingLocation = location.toDomainLayer(),
        outsideLocation = outsideLocation,
        meetingType = meetingType.toDomainLayer()
    )

    fun toInvitees(meeting: Meeting): List<Invitee> = inviteeIds.map {
        Invitee(userId = uuidFrom(it), meeting = meeting)
    }
}
