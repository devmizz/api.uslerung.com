package com.common

inline fun <T> Boolean?.isTrueThen(func: () -> T): T? = if (this == true) {
    func.invoke()
} else {
    null
}

inline fun <T> Boolean?.isFalseThen(func: () -> T): T? = if (this == false) {
    func.invoke()
} else {
    null
}

inline fun <T> Any?.isNullThen(func: () -> T): T? = if (this == null) {
    func.invoke()
} else {
    null
}

inline fun <T> Any?.isNotNullThen(func: () -> T): T? = if (this != null) {
    func.invoke()
} else {
    null
}

inline fun <T, U> Collection<T>.ifNotEmpty(func: (Collection<T>) -> Collection<U>): Collection<U> =
    if (this.isNotEmpty()) {
        func.invoke(this)
    } else {
        emptyList()
    }
