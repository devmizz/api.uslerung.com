package com.slack.config

import com.userapplication.config.UserApplicationConfig
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableConfigurationProperties(SlackSecretProperty::class)
@Import(UserApplicationConfig::class)
@ComponentScan(basePackages = ["com.slack"])
class SlackConfig
