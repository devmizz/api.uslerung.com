package com.scheduleapplication.config

import com.domain.config.DomainConfig
import com.scheduledomain.config.ScheduleDomainConfig
import com.userdomain.config.UserDomainConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@EnableAutoConfiguration
@Import(
    value = [
        DomainConfig::class,
        UserDomainConfig::class,
        ScheduleDomainConfig::class
    ]
)
@ComponentScan("com.scheduleapplication")
class ScheduleApplicationConfig
