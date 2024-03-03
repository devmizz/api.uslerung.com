package com.common

import java.util.UUID

fun randomUUID() = UUID.randomUUID()
fun randomStringUUID() = UUID.randomUUID().toString()
fun uuidFrom(str: String) = UUID.fromString(str)
