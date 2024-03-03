package com.client.schedule.dto.request

import com.client.global.dto.PresentationLayerRequestDTO
import com.client.schedule.vo.MeetingLocationInPresentation
import com.client.schedule.vo.MeetingTypeInPresentation
import com.scheduleapplication.dto.request.MeetingUpdateApplicationRequest
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

data class MeetingUpdatePresentationRequest(
    @field:Schema(title = "회의 식별자")
    val meetingId: String,

    @field:Schema(title = "회의 종류")
    val meetingType: MeetingTypeInPresentation,

    @field:Schema(title = "회의 제목")
    val title: String,

    @field:Schema(title = "회의 설명")
    val description: String,

    @field:Schema(title = "회의 날짜")
    val date: LocalDate,

    @field:Schema(title = "회의 시작 시간", defaultValue = "00:00:00")
    @field:DateTimeFormat(pattern = "HH:mm:ss")
    val beginAt: LocalTime,

    @field:Schema(title = "회의 종료 시간", defaultValue = "00:00:00")
    @field:DateTimeFormat(pattern = "HH:mm:ss")
    val endAt: LocalTime,

    @field:Schema(title = "회의 장소")
    val location: MeetingLocationInPresentation,

    @field:Schema(
        title = "외부 회의 장소",
        description = "회의 장소가 외부인 경우, 구체적인 장소 기입",
        required = false
    )
    val outsideLocation: String? = null,

    @field:Schema(title = "회의 참석자들의 유저 ID 목록")
    val inviteeIds: Set<UUID>
) : PresentationLayerRequestDTO {

    override fun toApplicationLayer(): MeetingUpdateApplicationRequest = MeetingUpdateApplicationRequest(
        meetingId = meetingId,
        meetingType = meetingType.toApplicationLayer(),
        title = title,
        description = description,
        date = date,
        beginAt = beginAt,
        endAt = endAt,
        location = location.toApplicationLayer(),
        outsideLocation = outsideLocation,
        inviteeUserIds = inviteeIds
    )
}
