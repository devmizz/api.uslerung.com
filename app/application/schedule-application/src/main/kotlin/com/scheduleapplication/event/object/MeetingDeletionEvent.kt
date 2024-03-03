package com.scheduleapplication.event.`object`

import com.scheduledomain.entity.Meeting
import com.scheduledomain.vo.MeetingLocation
import com.userdomain.entity.User
import java.time.LocalDateTime

data class MeetingDeletionEvent(
    val title: String,
    val beginAt: LocalDateTime,
    val endAt: LocalDateTime,
    val meetingLocation: String,
    val hostName: String,
    val users: List<MeetingDeletionEventMessageReceiver>
) {

    companion object {

        fun from(meeting: Meeting, users: List<User>): MeetingDeletionEvent {
            val meetingLocation = when (meeting.meetingLocation) {
                MeetingLocation.NO_WINDOW_CONFERENCE_ROOM -> "회의실 1"
                MeetingLocation.WINDOW_CONFERENCE_ROOM -> "회의실 2"
                MeetingLocation.OUTSIDE -> meeting.outsideLocation ?: "외부 장소가 정확히 지정되지 않았습니다."
            }

            val host = users.single { it.id == meeting.hostId }

            return MeetingDeletionEvent(
                title = meeting.title,
                beginAt = meeting.beginAt,
                endAt = meeting.endAt,
                meetingLocation = meetingLocation,
                hostName = host.slackUser.realName,
                users = users.map { MeetingDeletionEventMessageReceiver.from(it) }
            )
        }
    }

    private var userByLinked = users.groupBy { it.hasApprovalSlackLink }

    fun getLinkedUsers() = userByLinked[true] ?: emptyList()
    fun getUnlinkedUsers() = userByLinked[false] ?: emptyList()
}

data class MeetingDeletionEventMessageReceiver(
    val userId: String,
    val slackId: String,
    val hasApprovalSlackLink: Boolean
) {
    companion object {
        fun from(user: User): MeetingDeletionEventMessageReceiver = MeetingDeletionEventMessageReceiver(
            userId = user.id.toString(),
            slackId = user.slackUser.slackId,
            hasApprovalSlackLink = user.hasSlackLinkApproval
        )
    }
}
