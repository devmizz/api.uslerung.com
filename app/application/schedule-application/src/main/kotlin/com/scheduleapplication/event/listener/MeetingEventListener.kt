package com.scheduleapplication.event.listener

import com.common.uuidFrom
import com.domain.global.transaction.TransactionHandler
import com.scheduleapplication.event.`object`.MeetingConfirmEvent
import com.scheduleapplication.event.`object`.MeetingRefuseEvent
import com.scheduledomain.service.MeetingDomainService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MeetingEventListener(
    private val meetingDomainService: MeetingDomainService
) {

    @EventListener
    fun listen(event: MeetingConfirmEvent) = TransactionHandler.run {
        meetingDomainService.find(event.meetingId).confirmedBy(uuidFrom(event.userId))
    }

    @EventListener
    fun listen(event: MeetingRefuseEvent) = TransactionHandler.run {
        meetingDomainService.find(event.meetingId).refusedBy(uuidFrom(event.userId))
    }
}
