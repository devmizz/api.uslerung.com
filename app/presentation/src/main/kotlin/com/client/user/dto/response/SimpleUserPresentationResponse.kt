package com.client.user.dto.response

import com.userapplication.user.dto.response.SimpleUserApplicationResponse
import java.util.UUID

data class SimpleUserPresentationResponse(
    val id: UUID,
    val name: String,
    val hasSlackLinkApproval: Boolean
) {

    companion object {
        fun from(response: SimpleUserApplicationResponse): SimpleUserPresentationResponse {
            return SimpleUserPresentationResponse(
                id = response.id,
                name = response.name,
                hasSlackLinkApproval = response.hasSlackLinkApproval
            )
        }
    }
}
