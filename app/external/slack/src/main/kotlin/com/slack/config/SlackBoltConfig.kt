package com.slack.config

import com.slack.api.bolt.App
import com.slack.api.bolt.AppConfig
import com.slack.handler.SlackHandlerManager
import mu.KotlinLogging
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
class SlackBoltConfig(
    private val slackSecretProperty: SlackSecretProperty
) {

    @Bean
    fun loadSingleWorkspaceAppConfig(): AppConfig {
        return AppConfig.builder()
            .singleTeamBotToken(slackSecretProperty.botToken)
            .signingSecret(slackSecretProperty.signingSecret)
            .build()
    }

    @Bean
    fun app(config: AppConfig, slackHandlerManager: SlackHandlerManager): App {
        val app = App(config)

        slackHandlerManager.registerHandler(app)

        return app
    }
}
