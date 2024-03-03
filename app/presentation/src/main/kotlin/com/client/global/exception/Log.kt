package com.client.global.exception

fun errorLogFormat(errorId: String): String {
    return """
        ERROR ID: $errorId
    """.trimIndent()
}

fun errorLogFormat(errorId: String, description: String): String {
    return """
        ERROR ID: $errorId
        DESCRIPTION: $description
    """.trimIndent()
}
