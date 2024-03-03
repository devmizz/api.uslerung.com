package com.scheduleapplication.dto.response

import com.scheduledomain.entity.Meeting
import com.userdomain.entity.User
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

/**
 * 회의 정렬
 * YYYY-MM-DD 날짜 단위로 묶고(정렬)
 * HH-mm 시작 시간 단위로 묶고
 * 그 안에서 끝나는 시간 순서로 정렬
 */
data class MultipleMeetingApplicationResponse(
    val data: List<MultipleMeetingByDateApplicationResponse>
) {

    companion object {

        fun from(
            meetings: List<Meeting>,
            userById: Map<UUID, User>
        ): MultipleMeetingApplicationResponse {
            val simpleMeetingByDate = meetings
                .map { SimpleMeetingApplicationResponse.from(it, userById[it.hostId]) }
                .groupBy { it.beginAt.toLocalDate() }

            return MultipleMeetingApplicationResponse(
                simpleMeetingByDate.map { (date, meetings) ->
                    MultipleMeetingByDateApplicationResponse.from(date, meetings)
                }.sortedBy { it.date }
            )
        }
    }
}

data class MultipleMeetingByDateApplicationResponse(
    val date: LocalDate,
    val data: List<MultipleMeetingByTimeApplicationResponse>
) {

    companion object {

        fun from(
            date: LocalDate,
            meetings: List<SimpleMeetingApplicationResponse>
        ): MultipleMeetingByDateApplicationResponse = MultipleMeetingByDateApplicationResponse(
            date = date,
            data = meetings.groupBy { it.beginAt.toLocalTime() }
                .map { (beginAt, meetings) ->
                    MultipleMeetingByTimeApplicationResponse.from(beginAt, meetings)
                }.sortedBy { it.beginAt }
        )
    }
}

data class MultipleMeetingByTimeApplicationResponse(
    val beginAt: LocalTime,
    val dataSortedByEndAt: List<SimpleMeetingApplicationResponse>
) {

    companion object {

        fun from(
            beginAt: LocalTime,
            meetingsBeginAtSame: List<SimpleMeetingApplicationResponse>
        ): MultipleMeetingByTimeApplicationResponse = MultipleMeetingByTimeApplicationResponse(
            beginAt = beginAt,
            dataSortedByEndAt = meetingsBeginAtSame.sortedBy { it.endAt }
        )
    }
}
