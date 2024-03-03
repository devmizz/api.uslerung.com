package com.scheduleapplication.event.`object`

data class MeetingConfirmEvent(
    val meetingId: String,
    val userId: String
) {

    companion object {

        fun from(slackActionValue: String): MeetingConfirmEvent {
            val values = slackActionValue.split(", ")

            return MeetingConfirmEvent(
                meetingId = extractValue(values[0]),
                userId = extractValue(values[1])
            )
        }

        private fun extractValue(keyValue: String): String =
            keyValue.substring(keyValue.indexOf("(") + 1, keyValue.indexOf(")"))
    }
}
