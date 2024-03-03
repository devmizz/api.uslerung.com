package com.userapplication.slack.dto.request

import global.dto.ApplicationLayerRequestDTO

data class SlackUnlinkApplicationRequest(
    val userId: String
) : ApplicationLayerRequestDTO
