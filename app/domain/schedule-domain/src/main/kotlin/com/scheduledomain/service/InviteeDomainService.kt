package com.scheduledomain.service

import com.scheduledomain.entity.Invitee
import com.scheduledomain.repository.InviteeRepository
import org.springframework.stereotype.Service

@Service
class InviteeDomainService(
    private val inviteeRepository: InviteeRepository
) {

    fun add(invitees: List<Invitee>): List<Invitee> {
        return inviteeRepository.saveAll(invitees)
    }

    fun exclude(invitees: List<Invitee>) {
        return inviteeRepository.deleteAll(invitees)
    }
}
