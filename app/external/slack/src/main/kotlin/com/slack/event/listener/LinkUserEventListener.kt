package com.slack.event.listener

import com.slack.api.Slack
import com.slack.block.template.message.getUserApproveLinkBlockTemplate
import com.slack.config.SlackSecretProperty
import com.userapplication.event.LinkUserEvent
import mu.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class LinkUserEventListener(
    private val slackSecretProperty: SlackSecretProperty
) {

    @EventListener
    fun listen(event: LinkUserEvent) {
        val client = Slack.getInstance().methods()

        client.chatPostMessage { req ->
            req.token(slackSecretProperty.botToken)
                .channel(event.userSlackId)
                .blocks(getUserApproveLinkBlockTemplate())
                .text("어슬렁 연동이 완료되었어요!")
        }

        log.info { "연동 완료 메세지를 발송했습니다." }
    }
}
