package com.scheduledomain.entity

import com.common.error.CustomException
import com.domain.global.entity.BaseEntity
import com.scheduledomain.error.InviteeDomainError
import com.scheduledomain.vo.AttendanceDecisionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
class Invitee(
    userId: UUID,
    meeting: Meeting
) : BaseEntity() {

    @Column
    val userId: UUID = userId

    @Column
    @Enumerated(EnumType.STRING)
    var attendanceDecisionStatus: AttendanceDecisionStatus = AttendanceDecisionStatus.PENDING

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    val meeting: Meeting = meeting

    init {
        meeting.add(invitee = this)
    }

    fun confirm() {
        if (this.attendanceDecisionStatus == AttendanceDecisionStatus.CONFIRMED) {
            throw CustomException(InviteeDomainError.ALREADY_CONFIRMED)
        }

        this.attendanceDecisionStatus = AttendanceDecisionStatus.CONFIRMED
        meeting.confirmedBy(this.userId)
    }

    fun refuse() {
        if (this.attendanceDecisionStatus == AttendanceDecisionStatus.REFUSED) {
            throw CustomException(InviteeDomainError.ALREADY_REFUSED)
        }

        this.attendanceDecisionStatus = AttendanceDecisionStatus.REFUSED
        meeting.refusedBy(this.userId)
    }
}
