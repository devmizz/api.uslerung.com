package com.client.user.dto.request

import com.client.global.dto.PresentationLayerRequestDTO
import com.userapplication.slack.dto.request.SlackLinkApplicationRequest

data class SlackLinkPresentationRequest(
    val userId: String
) : PresentationLayerRequestDTO {

    override fun toApplicationLayer() = SlackLinkApplicationRequest(userId)
}
