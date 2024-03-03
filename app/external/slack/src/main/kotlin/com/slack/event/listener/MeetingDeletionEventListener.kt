package com.slack.event.listener

import com.scheduleapplication.event.`object`.MeetingDeletionEvent
import com.slack.api.Slack
import com.slack.block.template.message.getMeetingDeletionBlockTemplateForLinked
import com.slack.config.SlackSecretProperty
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MeetingDeletionEventListener(
    private val slackSecretProperty: SlackSecretProperty
) {

    @EventListener
    fun listen(event: MeetingDeletionEvent) {
        val client = Slack.getInstance().methods()

        event.getLinkedUsers()
            .forEach { user ->
                client.chatPostMessage { req ->
                    req.token(slackSecretProperty.botToken)
                        .channel(user.slackId)
                        .blocks(getMeetingDeletionBlockTemplateForLinked(event, user))
                        .text("회의가 취소됐어요!")
                }
            }
    }
}
