package com.client

import SwaggerConfig
import com.client.global.config.SecurityConfig
import com.client.security.property.JWTProperty
import com.scheduleapplication.config.ScheduleApplicationConfig
import com.slack.config.SlackConfig
import com.userapplication.config.UserApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.Import

@SpringBootApplication(
    scanBasePackageClasses = [ClientApplication::class]
)
@Import(
    value = [
        SecurityConfig::class,
        UserApplicationConfig::class,
        ScheduleApplicationConfig::class,
        SwaggerConfig::class,
        SlackConfig::class
    ]
)
@ConfigurationPropertiesScan
@EnableConfigurationProperties(JWTProperty::class)
@ServletComponentScan
class ClientApplication

fun main(args: Array<String>) {
    runApplication<ClientApplication>(*args)
}
