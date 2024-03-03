package com.client.user.dto.request

import com.client.global.dto.PresentationLayerRequestDTO
import com.userapplication.slack.dto.request.SlackUnlinkApplicationRequest

data class SlackUnlinkPresentationRequest(
    val userId: String
) : PresentationLayerRequestDTO {

    override fun toApplicationLayer(): SlackUnlinkApplicationRequest = SlackUnlinkApplicationRequest(userId)
}
