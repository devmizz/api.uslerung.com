package com.client.schedule.dto.response

import com.common.response
import com.common.responseForMinute
import com.scheduleapplication.dto.response.MultipleMeetingApplicationResponse
import com.scheduleapplication.dto.response.MultipleMeetingByDateApplicationResponse
import com.scheduleapplication.dto.response.MultipleMeetingByTimeApplicationResponse
import io.swagger.v3.oas.annotations.media.Schema

data class MultipleMeetingPresentationResponse(
    @field:Schema(description = "날짜 순으로 정렬된 회의 일정")
    val dataSortedByDate: List<MultipleMeetingByDatePresentationResponse>
) {

    companion object {

        fun from(response: MultipleMeetingApplicationResponse) = MultipleMeetingPresentationResponse(
            response.data.map { MultipleMeetingByDatePresentationResponse.from(it) }
        )
    }
}

data class MultipleMeetingByDatePresentationResponse(
    @field:Schema(description = "회의 일자", format = "yyyy-MM-dd")
    val date: String,
    @field:Schema(description = "시작 시간 순으로 정렬된 회의 일정")
    val dataSortedByBeginAt: List<MultipleMeetingByTimePresentationResponse>
) {

    companion object {

        fun from(response: MultipleMeetingByDateApplicationResponse) = MultipleMeetingByDatePresentationResponse(
            response.date.response(),
            response.data.map {
                MultipleMeetingByTimePresentationResponse.from(it)
            }
        )
    }
}

data class MultipleMeetingByTimePresentationResponse(
    @field:Schema(description = "시작 시간", format = "HH-mm")
    val beginAt: String,

    @field:Schema(description = "종료 시간 순으로 정렬된 회의 일정")
    val dataSortedByEndAt: List<SimpleMeetingPresentationResponse>
) {

    companion object {

        fun from(response: MultipleMeetingByTimeApplicationResponse) = MultipleMeetingByTimePresentationResponse(
            response.beginAt.responseForMinute(),
            response.dataSortedByEndAt.map { SimpleMeetingPresentationResponse.from(it) }
        )
    }
}
