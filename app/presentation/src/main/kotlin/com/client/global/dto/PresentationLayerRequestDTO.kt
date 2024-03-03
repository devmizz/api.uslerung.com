package com.client.global.dto

import global.dto.ApplicationLayerRequestDTO

interface PresentationLayerRequestDTO {

    fun toApplicationLayer(): ApplicationLayerRequestDTO
}
