package com.slack.block.maker

import com.slack.api.model.block.element.ButtonElement
import com.slack.api.model.kotlin_extension.block.element.ButtonElementBuilder
import com.slack.api.model.kotlin_extension.block.element.ButtonStyle

class BlockElementMaker {

    companion object {

        fun createButtonElement(
            text: String,
            actionId: String,
            value: String? = null,
            url: String? = null,
            style: ButtonStyle? = null
        ): ButtonElement {
            val builder = ButtonElementBuilder()

            builder.text(text, true)
            builder.actionId(actionId)
            value?.let { builder.value(it) }
            url?.let { builder.url(it) }
            style?.let { builder.style(it) }

            return builder.build()
        }
    }
}
