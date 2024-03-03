package com.slack.event.listener

import com.scheduleapplication.event.`object`.MeetingUpdateEvent
import com.slack.api.Slack
import com.slack.block.template.message.getMeetingUpdateBlockTemplate
import com.slack.config.SlackSecretProperty
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class MeetingUpdateEventListener(
    private val slackSecretProperty: SlackSecretProperty
) {

    @EventListener
    fun listen(event: MeetingUpdateEvent) {
        val client = Slack.getInstance().methods()

        event.getLinkedUsers()
            .forEach { user ->
                client.chatPostMessage { req ->
                    req.token(slackSecretProperty.botToken)
                        .channel(user.slackId)
                        .blocks(getMeetingUpdateBlockTemplate(event))
                        .text("회의 정보가 수정됐어요!")
                }
            }
    }
}
