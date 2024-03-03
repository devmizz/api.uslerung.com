package com.scheduledomain.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import com.scheduledomain.entity.Meeting
import com.scheduledomain.entity.QMeeting.meeting
import com.scheduledomain.vo.MeetingLocation
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class MeetingQueryRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findMeetingInPeriod(from: LocalDateTime, to: LocalDateTime): List<Meeting> =
        queryFactory.selectFrom(meeting)
            .where(
                (meeting.beginAt.eq(from).or(meeting.beginAt.after(from))).and(
                    meeting.endAt.before(to)
                )
            ).fetch()

    // 겹치는 게 있는지 판단
    // 다른 회의의 beginAt과 endAt -> from과 to 사이에 있으면 안 됨
    fun findDuplicateMeeting(from: LocalDateTime, to: LocalDateTime, location: MeetingLocation) =
        queryFactory.selectFrom(meeting)
            .where(
                (
                    (
                        (meeting.beginAt.before(from).or(meeting.beginAt.eq(from))).and(meeting.endAt.after(from))
                            .or(
                                meeting.beginAt.before(to).and(meeting.endAt.after(to).or(meeting.endAt.eq(to)))
                            )
                        ).or(meeting.beginAt.after(from).and(meeting.endAt.before(to)))
                    ).and(meeting.meetingLocation.eq(location))
            ).fetch()
}
