package com.scheduledomain.dto.request

import com.scheduledomain.vo.MeetingLocation
import com.scheduledomain.vo.MeetingType
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class MeetingUpdateDomainRequest(
    val meetingType: MeetingType,
    val title: String,
    val description: String,
    val date: LocalDate,
    val beginAt: LocalTime,
    val endAt: LocalTime,
    val location: MeetingLocation,
    val outsideLocation: String? = null,
    val inviteeIds: List<UUID>
)
