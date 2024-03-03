package com.userapplication.slack.service

import com.userapplication.slack.dto.param.SlackUserServiceParam
import com.userapplication.user.dto.param.UserInvitationApplicationParam
import org.springframework.stereotype.Service

@Service
interface SlackUserService {
    fun findUsersList(): List<SlackUserServiceParam>

    fun inviteUser(invitationParam: UserInvitationApplicationParam)
}
