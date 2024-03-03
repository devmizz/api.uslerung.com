package com.slack.block.template.component

import com.slack.api.model.kotlin_extension.block.element.ButtonStyle
import com.slack.block.maker.BlockElementMaker

fun getStartUslerungButtonElement() = BlockElementMaker.createButtonElement(
    actionId = "connect-slack",
    text = "어슬렁 시작하기 :robot_face:",
    url = "https://www.naver.com",
    style = ButtonStyle.PRIMARY
)

fun getMoveToUslerungButtonElement() = BlockElementMaker.createButtonElement(
    actionId = "connect-slack",
    text = "어슬렁 시작하기 :robot_face:",
    url = "https://www.naver.com",
    style = ButtonStyle.PRIMARY
)

fun getUserInvitationButtonElement() = BlockElementMaker.createButtonElement(
    actionId = "accept-invitation",
    text = "초대 수락하기",
    url = "https://www.naver.com"
)

fun getMeetingViewButtonElement(meetingId: String) = BlockElementMaker.createButtonElement(
    text = "자세히 보기",
    actionId = "view-meeting-detail",
    url = "https://www.naver.com/$meetingId"
)

fun getMeetingConfirmButtonElement(meetingId: String, userId: String) = BlockElementMaker.createButtonElement(
    text = "참석할게요 :ok_woman:",
    actionId = "confirm-meeting",
    value = "meetingId($meetingId), userId($userId)",
    style = ButtonStyle.PRIMARY
)

fun getMeetingRefuseButtonElement(meetingId: String, userId: String) = BlockElementMaker.createButtonElement(
    text = "불참이에요 :no_good:",
    actionId = "refuse-meeting",
    value = "meetingId($meetingId), userId($userId)",
    style = ButtonStyle.DANGER
)

fun getTurnoffAlertButtonElement(userId: String) = BlockElementMaker.createButtonElement(
    text = "회의 알림 차단:",
    actionId = "turnoff-meeting-alert",
    value = "userId($userId)",
    style = ButtonStyle.DANGER
)
