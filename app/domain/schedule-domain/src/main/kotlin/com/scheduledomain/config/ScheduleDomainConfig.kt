package com.scheduledomain.config

import com.domain.config.QuerydslConfig
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@Import(QuerydslConfig::class)
@ComponentScan(basePackages = ["com.scheduledomain"])
@EntityScan(basePackages = ["com.scheduledomain"])
@EnableJpaRepositories(basePackages = ["com.scheduledomain"])
@EnableAutoConfiguration
class ScheduleDomainConfig
