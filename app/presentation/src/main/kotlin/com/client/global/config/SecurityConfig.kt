package com.client.global.config

import com.client.global.exception.ExceptionHandlerFilter
import com.client.security.jwt.JWTFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtFilter: JWTFilter,
    private val exceptionHandlerFilter: ExceptionHandlerFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf { it.disable() }
            .cors { cors ->
                cors.configurationSource { request ->
                    val config = CorsConfiguration()
                    config.allowedOrigins = listOf("*")
                    config.allowedMethods = listOf("*")
                    config.allowedHeaders = listOf("*")
                    config
                }
            }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { req ->
                req
                    .requestMatchers(
                        "/v1/meetings/**"
                    ).authenticated()
                    .requestMatchers(
                        "/slack/events/**"
                    ).permitAll()
                    .requestMatchers(
                        "/**"
                    ).permitAll()
            }
            .addFilterBefore(jwtFilter, BasicAuthenticationFilter::class.java)
            .addFilterBefore(exceptionHandlerFilter, JWTFilter::class.java)
            .build()
}
