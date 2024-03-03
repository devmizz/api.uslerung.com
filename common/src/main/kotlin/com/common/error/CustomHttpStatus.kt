package com.common.error

enum class CustomHttpStatus : CustomHttpStatusCode {

    BAD_REQUEST { override val httpStatusCode = 400 },
    UNAUTHORIZED { override val httpStatusCode = 401 },
    FORBIDDEN { override val httpStatusCode = 403 },
    NOT_FOUND { override val httpStatusCode = 404 }
}
