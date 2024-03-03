package com.client.schedule.dto.response

import com.scheduleapplication.dto.response.MeetingDuplicateApplicationResponse

data class MeetingDuplicatePresentationResponse(
    val hasDuplicateMeeting: Boolean
) {

    companion object {

        fun from(response: MeetingDuplicateApplicationResponse) = MeetingDuplicatePresentationResponse(
            response.hasDuplicateMeeting
        )
    }
}
