package com.client.global.dto

import global.dto.ApplicationLayerResponseDTO

interface PresentationLayerResponseDTO {

    fun from(response: ApplicationLayerResponseDTO): PresentationLayerResponseDTO
}
