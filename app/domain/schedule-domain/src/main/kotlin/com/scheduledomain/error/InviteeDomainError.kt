package com.scheduledomain.error

import com.common.error.CustomError
import com.common.error.CustomHttpStatus

enum class InviteeDomainError : CustomError {

    ALREADY_CONFIRMED {
        override val customHttpStatus: CustomHttpStatus = CustomHttpStatus.BAD_REQUEST
        override val description: String = "이미 회의 참여자 명단에 있습니다."
    },
    ALREADY_REFUSED {
        override val customHttpStatus: CustomHttpStatus = CustomHttpStatus.BAD_REQUEST
        override val description: String = "이미 회의 불참자 명단에 있습니다."
    },
    NOT_INVITEE {
        override val customHttpStatus: CustomHttpStatus = CustomHttpStatus.BAD_REQUEST
        override val description: String = "초대 받지 못한 유저입니다."
    }
}
