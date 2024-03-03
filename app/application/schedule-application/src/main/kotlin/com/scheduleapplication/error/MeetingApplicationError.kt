package com.scheduleapplication.error

import com.common.error.CustomError
import com.common.error.CustomHttpStatus

enum class MeetingApplicationError : CustomError {

    MEETING_NOT_EXIST {
        override val customHttpStatus = CustomHttpStatus.NOT_FOUND
        override val description = "존재하지 않는 회의입니다."
    },

    NO_AUTHORITY_UPDATE {
        override val customHttpStatus = CustomHttpStatus.FORBIDDEN
        override val description = "수정할 수 있는 권한이 존재하지 않습니다."
    };
}
