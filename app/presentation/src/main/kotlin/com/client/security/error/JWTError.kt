package com.client.security.error

import com.common.error.CustomError
import com.common.error.CustomHttpStatus

enum class JWTError : CustomError {

    EXPIRED_TOKEN {
        override val customHttpStatus = CustomHttpStatus.UNAUTHORIZED
        override val description = "토큰이 만료됐습니다."
    },
    INVALID_TOKEN {
        override val customHttpStatus = CustomHttpStatus.UNAUTHORIZED
        override val description = "유효하지 않은 토큰입니다."
    },
    NEED_TOKEN {
        override val customHttpStatus = CustomHttpStatus.UNAUTHORIZED
        override val description = "토큰이 필요한 요청입니다."
    }
}
