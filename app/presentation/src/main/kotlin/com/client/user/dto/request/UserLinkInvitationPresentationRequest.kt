package com.client.user.dto.request

import com.common.uuidFrom
import com.userapplication.user.dto.request.UserInvitationApplicationRequest

data class UserLinkInvitationPresentationRequest(
    val hostId: String,
    val guestId: String
) {
    fun toApplicationRequest(): UserInvitationApplicationRequest {
        return UserInvitationApplicationRequest(uuidFrom(hostId), uuidFrom(guestId))
    }
}
