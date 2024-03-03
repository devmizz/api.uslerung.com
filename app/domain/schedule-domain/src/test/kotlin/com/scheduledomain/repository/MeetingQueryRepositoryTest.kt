package com.scheduledomain.repository

import com.scheduledomain.support.fixture.createMeetingFixture
import com.scheduledomain.vo.MeetingLocation
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@SpringBootTest
@Transactional
class MeetingQueryRepositoryTest(
    private val meetingRepository: MeetingRepository,
    private val meetingQueryRepository: MeetingQueryRepository
) : DescribeSpec({

    extension(SpringExtension)

    describe("MeetingQueryRepository") {

        context("시간 범위 내의 회의를 조회하는 경우") {

            it("시간 내의 회의가 존재하면 해당 회의를 반환한다.") {
                val from = LocalDateTime.of(2023, 12, 25, 14, 0, 0)
                val to = LocalDateTime.of(2023, 12, 25, 15, 0, 0)

                val meeting = meetingRepository.save(
                    createMeetingFixture(
                        beginAt = from,
                        endAt = to
                    )
                )

                val findMeeting = meetingQueryRepository.findMeetingInPeriod(
                    LocalDateTime.of(from.toLocalDate(), LocalTime.MIN),
                    LocalDateTime.of(from.toLocalDate(), LocalTime.MAX)
                )

                findMeeting.shouldContain(meeting)
            }
        }

        context("시간이 겹치는 회의를 조회하는 경우") {

            it("시간이 겹치는 회의들을 반환한다.") {
                val date = LocalDate.of(2024, 12, 25)
                meetingRepository.save(
                    createMeetingFixture(
                        beginAt = LocalDateTime.of(date, LocalTime.of(1, 0, 0)),
                        endAt = LocalDateTime.of(date, LocalTime.of(2, 0, 0)),
                        meetingLocation = MeetingLocation.WINDOW_CONFERENCE_ROOM
                    )
                )

                forAll(
                    // 왼쪽 경계 ~ 안
                    row(LocalDateTime.of(date, LocalTime.of(1, 0, 0)), LocalDateTime.of(date, LocalTime.of(1, 10, 0))),
                    // 안
                    row(LocalDateTime.of(date, LocalTime.of(1, 10, 0)), LocalDateTime.of(date, LocalTime.of(1, 20, 0))),
                    // 안 ~ 오른쪽 경계
                    row(LocalDateTime.of(date, LocalTime.of(1, 50, 0)), LocalDateTime.of(date, LocalTime.of(2, 0, 0))),
                    // 일치
                    row(LocalDateTime.of(date, LocalTime.of(1, 0, 0)), LocalDateTime.of(date, LocalTime.of(2, 0, 0))),
                    // 왼쪽 밖 ~ 안
                    row(LocalDateTime.of(date, LocalTime.of(0, 50, 0)), LocalDateTime.of(date, LocalTime.of(1, 10, 0))),
                    // 안 ~ 오른쪽 밖
                    row(LocalDateTime.of(date, LocalTime.of(1, 50, 0)), LocalDateTime.of(date, LocalTime.of(2, 10, 0))),
                    // 왼쪽 밖 ~ 오른쪽 밖
                    row(LocalDateTime.of(date, LocalTime.of(0, 50, 0)), LocalDateTime.of(date, LocalTime.of(2, 10, 0)))
                ) { from, to ->
                    meetingQueryRepository.findDuplicateMeeting(
                        from,
                        to,
                        MeetingLocation.WINDOW_CONFERENCE_ROOM
                    ).shouldNotBeEmpty()
                }
            }

            it("겹치는 회의가 없으면 빈 배열이 반환된다.") {
                val date = LocalDate.of(2024, 12, 25)
                meetingRepository.save(
                    createMeetingFixture(
                        beginAt = LocalDateTime.of(date, LocalTime.of(1, 0, 0)),
                        endAt = LocalDateTime.of(date, LocalTime.of(2, 0, 0)),
                        meetingLocation = MeetingLocation.WINDOW_CONFERENCE_ROOM
                    )
                )

                forAll(
                    // 왼쪽 밖 ~ 왼쪽 밖
                    row(LocalDateTime.of(date, LocalTime.of(0, 50, 0)), LocalDateTime.of(date, LocalTime.of(0, 55, 0))),
                    // 왼쪽 밖 ~ 왼쪽 경계
                    row(LocalDateTime.of(date, LocalTime.of(0, 50, 0)), LocalDateTime.of(date, LocalTime.of(1, 0, 0))),
                    // 오른쪽 경계 ~ 오른쪽 밖
                    row(LocalDateTime.of(date, LocalTime.of(2, 0, 0)), LocalDateTime.of(date, LocalTime.of(2, 10, 0))),
                    // 오른쪽 밖 ~ 오른쪽 밖
                    row(LocalDateTime.of(date, LocalTime.of(2, 5, 0)), LocalDateTime.of(date, LocalTime.of(2, 20, 0)))
                ) { from, to ->
                    meetingQueryRepository.findDuplicateMeeting(
                        from,
                        to,
                        MeetingLocation.WINDOW_CONFERENCE_ROOM
                    ).shouldBeEmpty()
                }
            }
        }
    }
})
