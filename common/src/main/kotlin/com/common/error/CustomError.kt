package com.common.error

interface CustomError {
    val customHttpStatus: CustomHttpStatus
    val description: String
}
