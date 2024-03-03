package com.domain.global.transaction

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TransactionAdvisor {

    @Transactional
    fun <T> run(function: () -> T): T {
        return function.invoke()
    }

    @Transactional(readOnly = true)
    fun <T> read(function: () -> T): T {
        return function.invoke()
    }
}
