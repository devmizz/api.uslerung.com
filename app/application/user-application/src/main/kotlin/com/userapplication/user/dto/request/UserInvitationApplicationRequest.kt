package com.userapplication.user.dto.request

import java.util.UUID

data class UserInvitationApplicationRequest(
    val hostId: UUID,
    val guestId: UUID
)
