package com.scheduleapplication.service

import com.common.error.CustomException
import com.domain.global.transaction.TransactionHandler
import com.scheduleapplication.dto.param.InviteesByUpdateApplicationParam
import com.scheduleapplication.dto.request.MeetingCreationApplicationRequest
import com.scheduleapplication.dto.request.MeetingUpdateApplicationRequest
import com.scheduleapplication.dto.response.MeetingApplicationResponse
import com.scheduleapplication.dto.response.MeetingDuplicateApplicationResponse
import com.scheduleapplication.dto.response.MultipleMeetingApplicationResponse
import com.scheduleapplication.error.MeetingApplicationError
import com.scheduleapplication.event.`object`.MeetingDeletionEvent
import com.scheduleapplication.event.`object`.MeetingInvitationEvent
import com.scheduleapplication.event.`object`.MeetingInvitationEventMessageReceiver
import com.scheduleapplication.event.`object`.MeetingUpdateEvent
import com.scheduleapplication.event.`object`.MeetingUpdateEventMessageReceiver
import com.scheduleapplication.vo.MeetingLocationInApplication
import com.scheduledomain.entity.Invitee
import com.scheduledomain.entity.Meeting
import com.scheduledomain.service.InviteeDomainService
import com.scheduledomain.service.MeetingDomainService
import com.userdomain.service.UserDomainService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class MeetingApplicationService(
    private val meetingDomainService: MeetingDomainService,
    private val inviteeDomainService: InviteeDomainService,
    private val userDomainService: UserDomainService,

    private val eventPublisher: ApplicationEventPublisher
) {

    fun createMeeting(
        request: MeetingCreationApplicationRequest,
        userId: String
    ): MeetingApplicationResponse = TransactionHandler.runWithReturn {
        val meeting = meetingDomainService.create(request.toMeeting(userId))
        val invitees = inviteeDomainService.add(request.toInvitees(meeting))
        val users = userDomainService.findUsers(invitees.map { it.userId })

        eventPublisher.publishEvent(
            MeetingInvitationEvent.from(
                meeting,
                users.map { MeetingInvitationEventMessageReceiver.from(it) }
            )
        )

        getMeetingApplicationResponse(meeting)
    }

    fun updateMeeting(
        request: MeetingUpdateApplicationRequest,
        userId: String
    ): MeetingApplicationResponse = TransactionHandler.runWithReturn {
        val meeting = meetingDomainService.find(request.meetingId).also { meeting ->
            if (!meeting.hasAuthority(userId)) {
                throw CustomException(MeetingApplicationError.NO_AUTHORITY_UPDATE)
            }

            meeting.update(request.toDomainLayerRequest())
        }

        val inviteesParamByUpdate = getInviteesByUpdate(meeting, request)
        updateInvitees(meeting, inviteesParamByUpdate)
        publishUpdateEvent(meeting, inviteesParamByUpdate)

        // 수정된 회의 정보 반환
        val userById = userDomainService.findUsers(
            meeting.invitees.map { it.userId } + meeting.hostId + meeting.creatorId
        ).associateBy { it.id }

        MeetingApplicationResponse.from(meeting, userById)
    }

    private fun getInviteesByUpdate(
        meeting: Meeting,
        request: MeetingUpdateApplicationRequest
    ): InviteesByUpdateApplicationParam {
        val existInviteeUserIds = meeting.invitees.map { it.userId }.toSet()

        return InviteesByUpdateApplicationParam(
            newInvitees = (request.inviteeUserIds - existInviteeUserIds).map { userId ->
                Invitee(userId, meeting)
            },
            noChangeInvitees = meeting.invitees.filter { it.userId in request.inviteeUserIds },
            excludedInvitees = meeting.invitees.filter { invitee ->
                invitee.userId in (existInviteeUserIds - request.inviteeUserIds)
            }
        )
    }

    /**
     * 회의 정렬
     * yyyy-MM-dd 날짜 단위로 묶고(정렬)
     * HH-mm 시작 시간 단위로 묶고
     * 그 안에서 끝나는 시간 순서로 정렬
     */
    fun findMeetings(
        from: LocalDate,
        to: LocalDate
    ): MultipleMeetingApplicationResponse = TransactionHandler.runWithReturn {
        val meetings = meetingDomainService.findsInPeriod(from, to)
        val userMap = userDomainService.findUsers(meetings.map { it.hostId }).associateBy { it.id }

        MultipleMeetingApplicationResponse.from(meetings, userMap)
    }

    fun hasDuplicateMeetingInOfflineLocation(
        from: LocalDateTime,
        to: LocalDateTime,
        locationInApplication: MeetingLocationInApplication
    ): MeetingDuplicateApplicationResponse = TransactionHandler.runWithReturn {
        MeetingDuplicateApplicationResponse(
            meetingDomainService.findDuplicateMeeting(from, to, locationInApplication.toDomainLayer()).isNotEmpty()
        )
    }

    fun deleteMeeting(id: String, userId: String) = TransactionHandler.run {
        val meeting = meetingDomainService.find(id)
        val users = userDomainService.findUsers(meeting.invitees.map { it.userId })

        meetingDomainService.delete(id, userId)

        eventPublisher.publishEvent(MeetingDeletionEvent.from(meeting, users))
    }

    private fun updateInvitees(
        meeting: Meeting,
        param: InviteesByUpdateApplicationParam
    ) {
        inviteeDomainService.add(param.newInvitees)
        meeting.add(param.newInvitees)

        inviteeDomainService.exclude(param.excludedInvitees)
        meeting.exclude(param.excludedInvitees)
    }

    /**
     * 신규 초대자 - 초대 이벤트
     * 기존 초대자 - 수정 이벤트
     * 제외 - 제외 이벤트 (아직 나오지 않아 수정 이벤트로 구현)
     */
    private fun publishUpdateEvent(meeting: Meeting, param: InviteesByUpdateApplicationParam) {
        eventPublisher.publishEvent(
            MeetingInvitationEvent.from(
                meeting = meeting,
                invitationMessageReceivers = userDomainService.findUsers(param.newInvitees.map { it.userId })
                    .map { user -> MeetingInvitationEventMessageReceiver.from(user) }
            )
        )

        eventPublisher.publishEvent(
            MeetingUpdateEvent.from(
                meeting = meeting,
                updateMessageReceivers = userDomainService.findUsers(
                    (param.noChangeInvitees + param.excludedInvitees).map { it.userId }
                ).map { user -> MeetingUpdateEventMessageReceiver.from(user) }
            )
        )
    }

    fun findMeeting(id: String): MeetingApplicationResponse = TransactionHandler.runWithReturn {
        val meetingResponse = meetingDomainService.find(id)
        getMeetingApplicationResponse(meetingResponse)
    }

    private fun getMeetingApplicationResponse(meeting: Meeting): MeetingApplicationResponse {
        val userById = userDomainService.findUsers(meeting.invitees.map { it.userId })
            .associateBy { it.id }

        return MeetingApplicationResponse.from(meeting, userById)
    }
}
