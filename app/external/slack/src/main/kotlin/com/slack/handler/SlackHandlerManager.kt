package com.slack.handler

import com.slack.api.bolt.App
import org.springframework.stereotype.Component

@Component
class SlackHandlerManager(
    private val slackHandlers: List<SlackHandler>
) {

    fun registerHandler(app: App) {
        slackHandlers.forEach { handler -> handler.handle(app) }
    }
}
