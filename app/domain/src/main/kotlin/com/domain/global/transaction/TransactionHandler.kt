package com.domain.global.transaction

import org.springframework.stereotype.Component

@Component
class TransactionHandler(
    _advisor: TransactionAdvisor
) {

    init {
        advisor = _advisor
    }

    companion object {

        private lateinit var advisor: TransactionAdvisor

        fun run(function: () -> Unit) {
            advisor.run(function)
        }

        fun <T> runWithReturn(function: () -> T): T {
            return advisor.run(function)
        }

        fun <T> readOnly(function: () -> T): T {
            return advisor.read(function)
        }
    }
}
