package com.slack.block.template.component

import com.slack.api.model.block.SectionBlock
import com.slack.api.model.block.element.ButtonElement
import com.slack.block.maker.LayoutBlockMaker

fun getMeetingInformationBlockTemplate(
    time: String,
    location: String,
    title: String,
    button: ButtonElement? = null
): SectionBlock {
    return LayoutBlockMaker.createSectionWithMarkdown(
        """
        $time | $location
        *$title*
        """.trimIndent(),
        button
    )
}
