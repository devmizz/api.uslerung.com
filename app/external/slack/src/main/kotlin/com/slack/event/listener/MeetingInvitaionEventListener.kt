package com.slack.event.listener

import com.scheduleapplication.event.`object`.MeetingInvitationEvent
import com.slack.api.Slack
import com.slack.block.template.message.getMeetingInvitationBlockTemplateForLUnlinked
import com.slack.block.template.message.getMeetingInvitationBlockTemplateForLinked
import com.slack.config.SlackSecretProperty
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MeetingInvitaionEventListener(
    private val slackSecretProperty: SlackSecretProperty
) {

    @EventListener
    fun listen(event: MeetingInvitationEvent) {
        val client = Slack.getInstance().methods()

        event.getLinkedUsers()
            .forEach { user ->
                client.chatPostMessage { req ->
                    req.token(slackSecretProperty.botToken)
                        .channel(user.slackId)
                        .blocks(getMeetingInvitationBlockTemplateForLinked(event, user))
                        .text("회의에 초대됐어요!")
                }
            }

        event.getUnlinkedUsers()
            .forEach { user ->
                client.chatPostMessage { req ->
                    req.token(slackSecretProperty.botToken)
                        .channel(user.slackId)
                        .blocks(getMeetingInvitationBlockTemplateForLUnlinked(event, user))
                        .text("회의에 초대됐어요!")
                }
            }
    }
}
