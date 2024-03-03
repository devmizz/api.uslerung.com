package com.client.global.exception

data class ExceptionResponse(
    val errorId: String,
    val description: String
) {

    companion object {
        fun of(errorId: String, description: String) = ExceptionResponse(errorId, description)
    }
}
