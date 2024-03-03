package com.slack.handler

import com.scheduleapplication.event.`object`.MeetingConfirmEvent
import com.scheduleapplication.event.`object`.MeetingRefuseEvent
import com.slack.api.bolt.App
import mu.KotlinLogging
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class SlackEventHandler(
    private val eventPublisher: ApplicationEventPublisher
) : SlackHandler {

    override fun handle(app: App) {
        app.blockAction("confirm-meeting") { req, ctx ->
            val value = req.payload.actions[0].value

            log.info { "회의 참석 요청 수신: $value" }

            eventPublisher.publishEvent(
                MeetingConfirmEvent.from(value)
            )

            ctx.ack()
        }

        app.blockAction("refuse-meeting") { req, ctx ->
            val value = req.payload.actions[0].value

            log.info { "회의 불참 요청 수신: $value" }

            eventPublisher.publishEvent(
                MeetingRefuseEvent.from(value)
            )

            ctx.ack()
        }
    }
}
