package com.common

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.response() = this.format(
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
)

fun LocalDateTime.responseForMinute() = this.format(
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
)

fun LocalDateTime.responseForDate() = this.format(
    DateTimeFormatter.ofPattern("yyyy-MM-dd")
)

fun LocalDateTime.responseForTime() = this.format(
    DateTimeFormatter.ofPattern("HH:mm:ss")
)

fun LocalDateTime.responseForSlackMessage() = this.format(
    DateTimeFormatter.ofPattern("yy년 M월 d일 a h시 m분").withLocale(Locale.KOREAN)
)

fun LocalDate.response() = this.format(
    DateTimeFormatter.ofPattern("yyyy-MM-dd")
)

fun LocalDate.responseForMonth() = this.format(
    DateTimeFormatter.ofPattern("yyyy-MM")
)

fun LocalTime.response() = this.format(
    DateTimeFormatter.ofPattern("HH:mm:ss")
)

fun LocalTime.responseForMinute() = this.format(
    DateTimeFormatter.ofPattern("HH:mm")
)

fun String.parseForDate() = LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

fun String.parseForMinute() = LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
