package com.client.schedule.dto.request

import com.client.global.dto.PresentationLayerRequestDTO
import com.client.schedule.vo.MeetingLocationInPresentation
import com.client.schedule.vo.MeetingTypeInPresentation
import com.scheduleapplication.dto.request.MeetingCreationApplicationRequest
import com.scheduleapplication.vo.MeetingLocationInApplication
import com.scheduleapplication.vo.MeetingTypeInApplication
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.time.LocalTime

data class MeetingCreationPresentationRequest(
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
    val inviteeIds: List<String>
) : PresentationLayerRequestDTO {

    override fun toApplicationLayer() = MeetingCreationApplicationRequest(
        meetingType = this.meetingType.toApplicationLayer() as MeetingTypeInApplication,
        title = this.title,
        description = this.description,
        date = this.date,
        beginAt = this.beginAt,
        endAt = this.endAt,
        location = this.location.toApplicationLayer() as MeetingLocationInApplication,
        outsideLocation = this.outsideLocation,
        inviteeIds = this.inviteeIds
    )
}
