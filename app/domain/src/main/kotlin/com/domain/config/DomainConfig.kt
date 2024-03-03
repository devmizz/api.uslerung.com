package com.domain.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@Configuration
@EnableAutoConfiguration
@EnableJpaAuditing
@ComponentScan(basePackages = ["com.domain"])
class DomainConfig
