package com.userdomain.error

import com.common.error.CustomError
import com.common.error.CustomHttpStatus

enum class UserError : CustomError {

    LINK_FAIL_NO_USER {
        override val customHttpStatus = CustomHttpStatus.NOT_FOUND
        override val description = "연동 대상 유저가 존재하지 않습니다."
    },
    ALREADY_LINKED_USER {
        override val customHttpStatus = CustomHttpStatus.BAD_REQUEST
        override val description = "이미 연동된 유저입니다."
    },

    UNLINK_FAIL_NO_USER {
        override val customHttpStatus = CustomHttpStatus.NOT_FOUND
        override val description = "연동 해제 대상 유저가 존재하지 않습니다."
    },
    UNLINKED_USER {
        override val customHttpStatus = CustomHttpStatus.BAD_REQUEST
        override val description = "기존에 연동이 되어 있지 않은 유저입니다."
    },

    NOT_EXIST_IN_SLACK {
        override val customHttpStatus = CustomHttpStatus.NOT_FOUND
        override val description = "슬랙 채널에 존재하지 않는 유저입니다."
    }
}
