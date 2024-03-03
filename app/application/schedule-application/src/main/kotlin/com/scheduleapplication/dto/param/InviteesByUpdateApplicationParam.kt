package com.scheduleapplication.dto.param

import com.scheduledomain.entity.Invitee

data class InviteesByUpdateApplicationParam(
    val newInvitees: List<Invitee>,
    val noChangeInvitees: List<Invitee>,
    val excludedInvitees: List<Invitee>
)
