package com.userapplication.user.service

import com.domain.global.transaction.TransactionHandler
import com.userapplication.slack.service.SlackUserService
import com.userapplication.user.dto.param.UserInvitationApplicationParam
import com.userapplication.user.dto.request.UserInvitationApplicationRequest
import com.userapplication.user.dto.response.SimpleUserApplicationResponse
import com.userdomain.service.UserDomainService
import org.springframework.stereotype.Service

@Service
class UserApplicationService(
    private val userDomainService: UserDomainService,
    private val slackUserService: SlackUserService
) {

    fun getUsers(): List<SimpleUserApplicationResponse> {
        val simpleUsers = userDomainService.findUsers()
        return simpleUsers.map { SimpleUserApplicationResponse.from(it) }
    }

    fun inviteUserToLinkSlack(request: UserInvitationApplicationRequest) = TransactionHandler.readOnly {
        val userById = userDomainService.findUsers(listOf(request.hostId, request.guestId)).associateBy { it.id }

        slackUserService.inviteUser(
            UserInvitationApplicationParam.from(userById[request.hostId]!!, userById[request.guestId]!!)
        )
    }
}
