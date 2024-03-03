package com.client.schedule.dto.response

import com.client.schedule.vo.MeetingLocationInPresentation
import com.client.schedule.vo.MeetingTypeInPresentation
import com.common.responseForMinute
import com.scheduleapplication.dto.response.SimpleMeetingApplicationResponse
import io.swagger.v3.oas.annotations.media.Schema

data class SimpleMeetingPresentationResponse(
    @field:Schema(description = "회의 시작 시간", pattern = "yyyy-MM-dd HH:mm")
    val beginAt: String,
    @field:Schema(description = "회의 종료 시간", pattern = "yyyy-MM-dd HH:mm")
    val endAt: String,
    val host: MeetingHostPresentationResponse,
    val title: String,
    val meetingLocation: MeetingLocationInPresentation,
    val meetingType: MeetingTypeInPresentation
) {

    companion object {

        fun from(
            response: SimpleMeetingApplicationResponse
        ) = SimpleMeetingPresentationResponse(
            beginAt = response.beginAt.responseForMinute(),
            endAt = response.endAt.responseForMinute(),
            host = MeetingHostPresentationResponse.from(response.host),
            title = response.title,
            meetingLocation = MeetingLocationInPresentation.from(response.meetingLocation),
            meetingType = MeetingTypeInPresentation.from(response.meetingType)
        )
    }
}
