package com.common

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TimeTest : DescribeSpec({

    describe("Time") {
        val date = LocalDate.of(2024, 12, 25)
        val time = LocalTime.of(14, 25, 35)

        context("LocalDateTime 자료형을") {
            val datetime = LocalDateTime.of(date, time)

            it("String 형태로 응답할 수 있다.") {
                datetime.response() shouldBe "2024-12-25 14:25:35"
            }

            it("분까지만 응답할 수 있다.") {
                datetime.responseForMinute() shouldBe "2024-12-25 14:25"
            }

            it("날짜만 응답할 수 있다.") {
                datetime.responseForDate() shouldBe "2024-12-25"
            }

            it("시간만 응답할 수 있다.") {
                datetime.responseForTime() shouldBe "14:25:35"
            }

            it("Slack에서 보일 형태로 변환할 수 있다.") {
                datetime.responseForSlackMessage() shouldBe "24년 12월 25일 오후 2시 25분"
                LocalDateTime.of(2007, 5, 6, 14, 3, 4).responseForSlackMessage() shouldBe "07년 5월 6일 오후 2시 3분"
                LocalDateTime.of(2007, 5, 6, 11, 3, 4).responseForSlackMessage() shouldBe "07년 5월 6일 오전 11시 3분"
            }
        }

        context("LocalDate 자료형을") {

            it("String 형태로 응답할 수 있다.") {
                date.response() shouldBe "2024-12-25"
            }

            it("월까지만 응답할 수 있다.") {
                date.responseForMonth() shouldBe "2024-12"
            }
        }

        context("LocalTime 자료형을") {

            it("String 형태로 응답할 수 있다.") {
                time.response() shouldBe "14:25:35"
            }

            it("분까지만 응답할 수 있다.") {
                time.responseForMinute() shouldBe "14:25"
            }
        }

        context("String 자료형을 LocalDateTime으로 파싱하는 경우") {

            it("yyyy-MM-dd HH:mm 형태를 파싱할 수 있다.") {
                "2024-12-25 14:25".parseForMinute() shouldBe LocalDateTime.of(2024, 12, 25, 14, 25, 0)
            }
        }

        context("String 자료형을 LocalDate로 파싱하는 경우") {

            it("yyyy-MM-dd 형태를 파싱할 수 있다.") {
                "2024-12-25".parseForDate() shouldBe LocalDate.of(2024, 12, 25)
            }
        }
    }
})
