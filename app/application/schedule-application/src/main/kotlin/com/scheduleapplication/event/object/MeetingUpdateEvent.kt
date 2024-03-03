package com.scheduleapplication.event.`object`

import com.scheduledomain.entity.Meeting
import com.scheduledomain.vo.MeetingLocation
import com.userdomain.entity.User
import java.time.LocalDateTime

data class MeetingUpdateEvent(
    val meetingId: String,
    val title: String,
    val beginAt: LocalDateTime,
    val endAt: LocalDateTime,
    val meetingLocation: String,
    val messageReceivers: List<MeetingUpdateEventMessageReceiver>
) {
    companion object {

        fun from(
            meeting: Meeting,
            updateMessageReceivers: List<MeetingUpdateEventMessageReceiver>
        ): MeetingUpdateEvent {
            val meetingLocation = when (meeting.meetingLocation) {
                MeetingLocation.NO_WINDOW_CONFERENCE_ROOM -> "회의실 1"
                MeetingLocation.WINDOW_CONFERENCE_ROOM -> "회의실 2"
                MeetingLocation.OUTSIDE -> meeting.outsideLocation ?: "외부 장소가 정확히 지정되지 않았습니다."
            }

            return MeetingUpdateEvent(
                meetingId = meeting.id.toString(),
                title = meeting.title,
                beginAt = meeting.beginAt,
                endAt = meeting.endAt,
                meetingLocation = meetingLocation,
                messageReceivers = updateMessageReceivers
            )
        }
    }

    private var userByLinked = messageReceivers.groupBy { it.hasApprovalSlackLink }

    fun getLinkedUsers() = userByLinked[true] ?: emptyList()
    fun getUnlinkedUsers() = userByLinked[false] ?: emptyList()
}

data class MeetingUpdateEventMessageReceiver(
    val userId: String,
    val slackId: String,
    val hasApprovalSlackLink: Boolean
) {
    companion object {
        fun from(user: User): MeetingUpdateEventMessageReceiver = MeetingUpdateEventMessageReceiver(
            userId = user.id.toString(),
            slackId = user.slackUser.slackId,
            hasApprovalSlackLink = user.hasSlackLinkApproval
        )
    }
}
