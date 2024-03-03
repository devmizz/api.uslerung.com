package com.scheduledomain.`object`

import com.common.and
import com.common.error.CustomException
import com.scheduledomain.entity.Invitee
import com.scheduledomain.error.InviteeDomainError
import com.scheduledomain.vo.AttendanceDecisionStatus
import com.scheduledomain.vo.AttendanceDecisionStatus.CONFIRMED
import com.scheduledomain.vo.AttendanceDecisionStatus.PENDING
import com.scheduledomain.vo.AttendanceDecisionStatus.REFUSED
import java.util.UUID

class Invitees private constructor(
    invitees: List<Invitee>
) {

    companion object {

        fun from(invitees: List<Invitee>): Invitees {
            return Invitees(invitees)
        }
    }

    private val invitees: MutableMap<AttendanceDecisionStatus, MutableList<Invitee>> = invitees.groupBy { invitee ->
        invitee.attendanceDecisionStatus
    }.mapValues { (_, v) ->
        v.toMutableList()
    }.toMutableMap()

    fun getInviteesByStatus(status: AttendanceDecisionStatus) = when (status) {
        PENDING -> invitees[PENDING]?.toList() ?: emptyList()
        CONFIRMED -> invitees[CONFIRMED]?.toList() ?: emptyList()
        REFUSED -> invitees[REFUSED]?.toList() ?: emptyList()
    }

    fun addNew(invitee: Invitee) {
        (invitees[PENDING] ?: mutableListOf()).add(invitee)
    }

    fun addNew(newInvitees: List<Invitee>) {
        (invitees[PENDING] ?: mutableListOf()).addAll(newInvitees)
    }

    fun exclude(invitee: Invitee) {
        invitees.filterValues { invitees -> invitees.contains(invitee) }
            .mapValues { (_, v) -> v.remove(invitee) }
    }

    fun exclude(excludedInvitees: List<Invitee>) {
        invitees.mapValues { (_, inviteesByStatus) ->
            inviteesByStatus -= excludedInvitees
        }
    }

    fun confirmedBy(userId: UUID) {
        if (invitees[CONFIRMED]?.any { invitee -> invitee.userId == userId } == true) {
            throw CustomException(InviteeDomainError.ALREADY_CONFIRMED)
        }

        addOnSpecificStatus(CONFIRMED, userId)
    }

    fun refusedBy(userId: UUID) {
        if (invitees[CONFIRMED]?.any { invitee -> invitee.userId == userId } == true) {
            throw CustomException(InviteeDomainError.ALREADY_CONFIRMED)
        }

        addOnSpecificStatus(REFUSED, userId)
    }

    private fun addOnSpecificStatus(requestStatus: AttendanceDecisionStatus, userId: UUID) {
        val (existStatus, invitee) = getStatusAndInvitee(userId)
        invitees[existStatus]!!.remove(invitee)
        invitees[requestStatus]!!.add(invitee)
    }

    private fun getStatusAndInvitee(userId: UUID): Pair<AttendanceDecisionStatus, Invitee> {
        for ((status, invitees) in invitees) {
            val invitee = try {
                invitees.single { invitee -> invitee.userId == userId }
            } catch (e: NoSuchElementException) {
                continue
            }

            return status and invitee
        }

        throw CustomException(InviteeDomainError.NOT_INVITEE)
    }
}
