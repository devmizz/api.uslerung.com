package com.slack.block.template.message

import com.slack.api.model.block.Blocks
import com.slack.block.maker.LayoutBlockMaker
import com.slack.block.template.component.getStartUslerungButtonElement
import com.slack.block.template.component.getUserInvitationButtonElement

fun getRequestLinkBlockTemplate() = Blocks.asBlocks(
    LayoutBlockMaker.createSectionWithMarkdown(
        """
        회의실 유랑자들의 똑똑한 알림봇 *어슬렁*:robot_face:에 유저이름 님을 초대합니다!
        """.trimIndent()
    ),
    LayoutBlockMaker.createSectionWithMarkdown(
        """
        • 귀찮은 회의실 일정 관리? 어슬렁이 다 해드릴게요!
        • 회의 10분 전 알림을 보내드릴게요.
        • 지각자에게 일일히 메시지 보낼 필요없어요. 클릭 한 번으로 지각자 소환!
        • 회의실 사용을 연장하고 싶으세요? 다음 회의실 사용자에게 대신 물어봐드릴게요.
        • 더이상 사용하고 싶지 않다면, 언제든 연동을 해제할 수 있어요.
        """.trimIndent()
    ),
    LayoutBlockMaker.createActionsBlock(
        elements = arrayOf(getStartUslerungButtonElement())
    )
)

fun getUserApproveLinkBlockTemplate() = Blocks.asBlocks(
    LayoutBlockMaker.createSectionWithMarkdown(
        """
        :robot_face: *어슬렁 연동이 완료되었어요!*
        """.trimIndent()
    ),
    LayoutBlockMaker.createSectionWithMarkdown(
        """
        어슬렁과 함께해 주셔서 감사합니다. 앞으로 슬기로운 회의생활을 위해 어슬렁이 도와드릴게요!

        어슬렁을 더욱 편리하게 이용하실 수 있도록 명령어 사용법도 읽어주세요:slightly_smiling_face:

        1. 어슬렁 채팅방에 입장한다.
        2. 채팅창에 `/`를 입력하고, 원하는 명령어를 입력한다.
        3. 궁금한 점이 있다면 `/궁금해요` 입력
        4. 불편한 점이 있거나 필요한 기능이 있다면 `/건의해요` 입력
        """.trimIndent()
    )
)

fun getUserInvitationBlockTemplate(hostName: String) = Blocks.asBlocks(
    LayoutBlockMaker.createSectionWithMarkdown(
        """
        :love_letter: *$hostName 님께서 어슬렁 초대장을 보냈어요!*
        """.trimIndent()
    ),
    LayoutBlockMaker.createSectionWithMarkdown(
        """
        회의실 유랑자들의 똑똑한 알림봇 *어슬렁*:robot_face:과 함께하지 않으실래요?
        회의실 일정 관리, 회의 10분 전 알림, 지각자 호출 등
        회의와 관련된 모든 활동을 간편하게 해결할 수 있어요!
        """.trimIndent()
    ),
    LayoutBlockMaker.createActionsBlock(
        getUserInvitationButtonElement()
    )
)
