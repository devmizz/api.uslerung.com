package com.scheduleapplication.event.`object`

import com.scheduledomain.entity.Meeting
import com.scheduledomain.vo.MeetingLocation
import com.userdomain.entity.User
import java.time.LocalDateTime

data class MeetingInvitationEvent(
    val meetingId: String,
    val title: String,
    val beginAt: LocalDateTime,
    val endAt: LocalDateTime,
    val meetingLocation: String,
    val messageReceivers: List<MeetingInvitationEventMessageReceiver>
) {
    companion object {

        fun from(
            meeting: Meeting,
            invitationMessageReceivers: List<MeetingInvitationEventMessageReceiver>
        ): MeetingInvitationEvent {
            val meetingLocation = when (meeting.meetingLocation) {
                MeetingLocation.NO_WINDOW_CONFERENCE_ROOM -> "회의실 1"
                MeetingLocation.WINDOW_CONFERENCE_ROOM -> "회의실 2"
                MeetingLocation.OUTSIDE -> meeting.outsideLocation ?: "외부 장소가 정확히 지정되지 않았습니다."
            }

            return MeetingInvitationEvent(
                meetingId = meeting.id.toString(),
                title = meeting.title,
                beginAt = meeting.beginAt,
                endAt = meeting.endAt,
                meetingLocation = meetingLocation,
                messageReceivers = invitationMessageReceivers
            )
        }
    }

    private var userByLinked = messageReceivers.groupBy { it.hasApprovalSlackLink }

    fun getLinkedUsers() = userByLinked[true] ?: emptyList()
    fun getUnlinkedUsers() = userByLinked[false] ?: emptyList()
}

data class MeetingInvitationEventMessageReceiver(
    val userId: String,
    val slackId: String,
    val hasApprovalSlackLink: Boolean
) {
    companion object {
        fun from(user: User): MeetingInvitationEventMessageReceiver = MeetingInvitationEventMessageReceiver(
            userId = user.id.toString(),
            slackId = user.slackUser.slackId,
            hasApprovalSlackLink = user.hasSlackLinkApproval
        )
    }
}
