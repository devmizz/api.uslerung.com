package com.userapplication.slack.dto.request

import global.dto.ApplicationLayerRequestDTO

data class SlackLinkApplicationRequest(
    val userId: String
) : ApplicationLayerRequestDTO
