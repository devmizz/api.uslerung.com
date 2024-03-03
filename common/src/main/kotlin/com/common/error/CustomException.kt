package com.common.error

class CustomException(
    val error: CustomError
) : RuntimeException(error.description)
