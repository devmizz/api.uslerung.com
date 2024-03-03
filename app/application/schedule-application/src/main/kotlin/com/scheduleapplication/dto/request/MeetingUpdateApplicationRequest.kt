package com.scheduleapplication.dto.request

import com.scheduleapplication.vo.MeetingLocationInApplication
import com.scheduleapplication.vo.MeetingTypeInApplication
import com.scheduledomain.dto.request.MeetingUpdateDomainRequest
import global.dto.ApplicationLayerRequestDTO
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class MeetingUpdateApplicationRequest(
    val meetingId: String,
    val meetingType: MeetingTypeInApplication,
    val title: String,
    val description: String,
    val date: LocalDate,
    val beginAt: LocalTime,
    val endAt: LocalTime,
    val location: MeetingLocationInApplication,
    val outsideLocation: String? = null,
    val inviteeUserIds: Set<UUID>
) : ApplicationLayerRequestDTO {

    fun toDomainLayerRequest(): MeetingUpdateDomainRequest = MeetingUpdateDomainRequest(
        meetingType = this.meetingType.toDomainLayer(),
        title = this.title,
        description = this.description,
        date = this.date,
        beginAt = this.beginAt,
        endAt = this.endAt,
        location = this.location.toDomainLayer(),
        outsideLocation = this.outsideLocation,
        inviteeIds = this.inviteeUserIds.toList()
    )
}
