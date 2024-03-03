package com.scheduledomain.service

import com.common.error.CustomException
import com.common.isFalseThen
import com.common.uuidFrom
import com.scheduledomain.entity.Meeting
import com.scheduledomain.error.MeetingDomainError
import com.scheduledomain.repository.MeetingQueryRepository
import com.scheduledomain.repository.MeetingRepository
import com.scheduledomain.vo.MeetingLocation
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class MeetingDomainService(
    private val meetingRepository: MeetingRepository,
    private val meetingQueryRepository: MeetingQueryRepository
) {

    fun create(meeting: Meeting): Meeting = meetingRepository.save(meeting)

    fun find(id: String): Meeting = meetingRepository.findByIdOrNull(uuidFrom(id))
        ?: throw CustomException(MeetingDomainError.FIND_MEETING_NOT_EXIST)

    fun findsInPeriod(from: LocalDate, to: LocalDate): List<Meeting> = meetingQueryRepository.findMeetingInPeriod(
        LocalDateTime.of(from, LocalTime.MIN),
        LocalDateTime.of(to, LocalTime.MAX)
    )

    fun findDuplicateMeeting(
        from: LocalDateTime,
        to: LocalDateTime,
        location: MeetingLocation
    ): List<Meeting> = meetingQueryRepository.findDuplicateMeeting(from, to, location)

    fun delete(meeting: Meeting, userId: String) {
        meeting.hasAuthority(userId).isFalseThen { throw CustomException(MeetingDomainError.NO_DELETE_AUTHORITY) }

        meetingRepository.delete(meeting)
    }

    fun delete(id: String, userId: String) {
        val meeting = meetingRepository.findByIdOrNull(uuidFrom(id))
            ?: throw CustomException(MeetingDomainError.DELETE_MEETING_NOT_EXIST)

        meeting.hasAuthority(userId).isFalseThen { throw CustomException(MeetingDomainError.NO_DELETE_AUTHORITY) }

        meetingRepository.delete(meeting)
    }
}
