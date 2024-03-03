package com.slack.handler

import com.slack.api.bolt.App
import com.slack.block.template.message.getRequestLinkBlockTemplate
import mu.KotlinLogging
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class SlackCommandHandler : SlackHandler {

    override fun handle(app: App) {
        app.command("/어슬렁연동하기") { req, ctx ->
            log.info { "연동 요청이 들어왔습니다." }
            ctx.ack(getRequestLinkBlockTemplate())
        }
    }
}
