package com.slack.user

import com.slack.api.Slack
import com.slack.api.methods.request.users.UsersListRequest
import com.slack.block.template.message.getUserInvitationBlockTemplate
import com.slack.config.SlackSecretProperty
import com.userapplication.slack.dto.param.SlackUserServiceParam
import com.userapplication.slack.service.SlackUserService
import com.userapplication.user.dto.param.UserInvitationApplicationParam
import org.springframework.stereotype.Service

@Service
class SlackUserServiceImpl(
    private val slackSecretProperty: SlackSecretProperty,

    private val slackUserConverter: SlackUserConverter
) : SlackUserService {

    override fun findUsersList(): List<SlackUserServiceParam> {
        val slack = Slack.getInstance()
        val response = slack.methods(slackSecretProperty.botToken).usersList(
            UsersListRequest.builder().build()
        )

        return slackUserConverter.convertSlackUser(response)
    }

    override fun inviteUser(invitationParam: UserInvitationApplicationParam) {
        val client = Slack.getInstance().methods()

        client.chatPostMessage { req ->
            req.token(slackSecretProperty.botToken)
                .channel(invitationParam.guestSlackId)
                .blocks(getUserInvitationBlockTemplate(invitationParam.hostName))
        }
    }
}
