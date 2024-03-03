package com.userapplication.support.fixture

import com.common.randomStringUUID
import com.userapplication.slack.dto.param.SlackUserServiceParam

fun createSlackUserServiceParam(
    slackId: String = randomStringUUID(),
    teamId: String = randomStringUUID(),
    displayName: String = randomStringUUID(),
    realName: String = randomStringUUID(),
    email: String = randomStringUUID(),
    imageOrigin: String = randomStringUUID()
) = SlackUserServiceParam(
    slackId,
    teamId,
    displayName,
    realName,
    email,
    imageOrigin
)
