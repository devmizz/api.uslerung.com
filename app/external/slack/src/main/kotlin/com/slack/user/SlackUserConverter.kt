package com.slack.user

import com.slack.api.methods.response.users.UsersListResponse
import com.userapplication.slack.dto.param.SlackUserServiceParam
import org.springframework.stereotype.Component

@Component
class SlackUserConverter {

    fun convertSlackUser(users: UsersListResponse): List<SlackUserServiceParam> {
        return users.members
            .filter { !it.isBot && it.profile.email != null }
            .map { user ->
                SlackUserServiceParam(
                    slackId = user.id,
                    teamId = user.teamId,
                    displayName = user.profile.displayName,
                    realName = user.profile.realName,
                    email = user.profile.email,
                    imageOrigin = user.profile.imageOriginal
                )
            }
    }
}
