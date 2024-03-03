package com.scheduleapplication.event.`object`

data class MeetingRefuseEvent(
    val meetingId: String,
    val userId: String
) {

    companion object {

        fun from(slackActionValue: String): MeetingRefuseEvent {
            val values = slackActionValue.split(", ")

            return MeetingRefuseEvent(extractValue(values[0]), extractValue(values[1]))
        }

        private fun extractValue(keyValue: String): String =
            keyValue.substring(keyValue.indexOf("(") + 1, keyValue.indexOf(")"))
    }
}
