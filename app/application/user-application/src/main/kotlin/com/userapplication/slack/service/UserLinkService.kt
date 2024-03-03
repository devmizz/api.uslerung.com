package com.userapplication.slack.service

import com.common.uuidFrom
import com.domain.global.transaction.TransactionHandler
import com.userapplication.event.LinkUserEvent
import com.userapplication.slack.dto.request.SlackLinkApplicationRequest
import com.userapplication.slack.dto.request.SlackUnlinkApplicationRequest
import com.userdomain.service.UserDomainService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class UserLinkService(
    private val userDomainService: UserDomainService,

    private val eventPublisher: ApplicationEventPublisher
) {

    fun linkUser(request: SlackLinkApplicationRequest): Unit = TransactionHandler.run {
        val user = userDomainService.linkWithSlack(uuidFrom(request.userId))
        eventPublisher.publishEvent(LinkUserEvent.from(user))
    }

    fun unlinkUser(request: SlackUnlinkApplicationRequest): Unit = TransactionHandler.run {
        userDomainService.unlinkWithSlack(uuidFrom(request.userId))
    }
}
