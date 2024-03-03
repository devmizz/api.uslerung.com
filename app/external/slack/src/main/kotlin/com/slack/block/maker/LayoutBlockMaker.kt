package com.slack.block.maker

import com.slack.api.model.block.ActionsBlock
import com.slack.api.model.block.Blocks.actions
import com.slack.api.model.block.Blocks.section
import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.composition.BlockCompositions.markdownText
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.element.BlockElement
import com.slack.api.model.block.element.ButtonElement

class LayoutBlockMaker {

    companion object {

        fun createSectionWithMarkdown(text: String, accessory: BlockElement? = null): SectionBlock =
            section { section ->
                val result = section.text(markdownText(text))
                accessory?.let { section.accessory(it) }

                result
            }

        fun createSectionWithPlainText(text: String, accessory: BlockElement? = null): SectionBlock =
            section { section ->
                val result = section.text(plainText(text))
                accessory?.let { result.accessory(it) }

                result
            }

        fun createActionsBlock(
            vararg elements: ButtonElement
        ): ActionsBlock = actions {
            it.elements(elements.toList())
        }
    }
}
