package com.scheduledomain.error

import com.common.error.CustomError
import com.common.error.CustomHttpStatus

enum class MeetingDomainError : CustomError {

    FIND_MEETING_NOT_EXIST {
        override val customHttpStatus = CustomHttpStatus.NOT_FOUND
        override val description = "찾는 회의가 존재하지 않습니다."
    },

    DELETE_MEETING_NOT_EXIST {
        override val customHttpStatus = CustomHttpStatus.NOT_FOUND
        override val description = "지우려는 회의가 존재하지 않습니다."
    },

    NO_DELETE_AUTHORITY {
        override val customHttpStatus = CustomHttpStatus.UNAUTHORIZED
        override val description = "삭제 권한이 없습니다."
    },

    ALREADY_CONFIRMED {
        override val customHttpStatus = CustomHttpStatus.BAD_REQUEST
        override val description = "이미 회의 참석"
    }
}
