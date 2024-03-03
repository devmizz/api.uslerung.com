package com.slack.block.template.message

import com.common.responseForSlackMessage
import com.scheduleapplication.event.`object`.MeetingDeletionEvent
import com.scheduleapplication.event.`object`.MeetingDeletionEventMessageReceiver
import com.scheduleapplication.event.`object`.MeetingInvitationEvent
import com.scheduleapplication.event.`object`.MeetingInvitationEventMessageReceiver
import com.scheduleapplication.event.`object`.MeetingUpdateEvent
import com.slack.api.model.block.Blocks
import com.slack.api.model.block.LayoutBlock
import com.slack.block.maker.LayoutBlockMaker
import com.slack.block.template.component.getMeetingConfirmButtonElement
import com.slack.block.template.component.getMeetingInformationBlockTemplate
import com.slack.block.template.component.getMeetingRefuseButtonElement
import com.slack.block.template.component.getMeetingViewButtonElement
import com.slack.block.template.component.getMoveToUslerungButtonElement
import com.slack.block.template.component.getStartUslerungButtonElement
import com.slack.block.template.component.getTurnoffAlertButtonElement

fun getMeetingInvitationBlockTemplateForLinked(
    event: MeetingInvitationEvent,
    user: MeetingInvitationEventMessageReceiver
): List<LayoutBlock> {
    return Blocks.asBlocks(
        LayoutBlockMaker.createSectionWithMarkdown(
            """
            :robot_face: *새로운 회의에 초대되었어요!*
            """.trimIndent()
        ),
        getMeetingInformationBlockTemplate(
            event.beginAt.responseForSlackMessage(),
            event.meetingLocation,
            event.title,
            getMeetingViewButtonElement(event.meetingId)
        ),
        LayoutBlockMaker.createSectionWithPlainText(
            """
            회의 정보를 확인하셨다면, 참석 여부를 선택해 주세요.
            참석하신다면, 회의 10분 전에 알림을 보내드릴게요.
            """.trimIndent()
        ),
        LayoutBlockMaker.createActionsBlock(
            getMeetingConfirmButtonElement(event.meetingId, user.userId),
            getMeetingRefuseButtonElement(event.meetingId, user.userId)
        )
    )
}

fun getMeetingInvitationBlockTemplateForLUnlinked(
    event: MeetingInvitationEvent,
    user: MeetingInvitationEventMessageReceiver
): List<LayoutBlock> {
    return Blocks.asBlocks(
        LayoutBlockMaker.createSectionWithMarkdown(
            """
            :robot_face: *새로운 회의에 초대되었어요!*
            """.trimIndent()
        ),
        getMeetingInformationBlockTemplate(
            event.beginAt.responseForSlackMessage(),
            event.meetingLocation,
            event.title,
            getMeetingViewButtonElement(event.meetingId)
        ),
        LayoutBlockMaker.createSectionWithPlainText(
            """
            회의 정보를 확인하셨다면, 참석 여부를 선택해 주세요.
            참석하신다면, 회의 10분 전에 알림을 보내드릴게요.
            """.trimIndent()
        ),
        LayoutBlockMaker.createActionsBlock(
            getStartUslerungButtonElement(),
            getTurnoffAlertButtonElement(user.userId)
        )
    )
}

fun getMeetingUpdateBlockTemplate(
    event: MeetingUpdateEvent
): List<LayoutBlock> {
    return Blocks.asBlocks(
        LayoutBlockMaker.createSectionWithMarkdown(
            """
            :rotating_light: *회의 정보가 업데이트되었어요!*
            착오가 없도록 업데이트된 정보를 꼭 확인해 주세요.
            """.trimIndent()
        ),
        getMeetingInformationBlockTemplate(
            event.beginAt.responseForSlackMessage(),
            event.meetingLocation,
            event.title,
            getMeetingViewButtonElement(event.meetingId)
        )
    )
}

fun getMeetingDeletionBlockTemplateForLinked(
    event: MeetingDeletionEvent,
    user: MeetingDeletionEventMessageReceiver
): List<LayoutBlock> {
    return Blocks.asBlocks(
        LayoutBlockMaker.createSectionWithMarkdown(
            """
            :rotating_light: *회의가 취소되었어요!*
            """.trimIndent()
        ),
        LayoutBlockMaker.createSectionWithPlainText(
            """
            ${event.hostName} 님께서 아래 회의 일정을 취소하셨어요.
            착오가 없도록 삭제된 회의 일정을 꼭 확인해 주세요.
            """.trimIndent()
        ),
        getMeetingInformationBlockTemplate(
            event.beginAt.responseForSlackMessage(),
            event.meetingLocation,
            event.title
        ),
        LayoutBlockMaker.createActionsBlock(
            getMoveToUslerungButtonElement()
        )
    )
}
