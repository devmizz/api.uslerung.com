package com.scheduledomain.entity

import com.common.uuidFrom
import com.scheduledomain.dto.request.MeetingUpdateDomainRequest
import com.scheduledomain.`object`.Invitees
import com.scheduledomain.vo.AttendanceDecisionStatus
import com.scheduledomain.vo.MeetingLocation
import com.scheduledomain.vo.MeetingType
import jakarta.persistence.CascadeType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.PostLoad
import jakarta.persistence.Transient
import java.time.LocalDateTime
import java.util.UUID

@Entity
@DiscriminatorValue("MEETING")
class Meeting(
    creatorId: UUID,
    beginAt: LocalDateTime,
    endAt: LocalDateTime,
    hostId: UUID,
    title: String,
    description: String,
    meetingLocation: MeetingLocation,
    outsideLocation: String?,
    meetingType: MeetingType
) : Schedule() {

    override val creatorId: UUID = creatorId

    override var beginAt: LocalDateTime = beginAt

    override var endAt: LocalDateTime = endAt

    val hostId: UUID = hostId

    var title: String = title
        protected set

    var description: String = description
        protected set

    @Enumerated(EnumType.STRING)
    var meetingLocation: MeetingLocation = meetingLocation
        protected set

    var outsideLocation: String? = outsideLocation
        protected set

    @Enumerated(EnumType.STRING)
    var meetingType: MeetingType = meetingType
        protected set

    @OneToMany(mappedBy = "meeting", cascade = [CascadeType.ALL], targetEntity = Invitee::class)
    var invitees: List<Invitee> = listOf()
        protected set

    // invitees 관련 메서드를 편하게 처리하도록 만든 일급 컬렉션
    @Transient
    private var _invitees: Invitees = Invitees.from(invitees)

    @PostLoad
    fun initializeTransientProperty() {
        _invitees = Invitees.from(invitees)
    }

    // 초대자 관련 처리는 Invitee에서 수행
    fun update(request: MeetingUpdateDomainRequest) {
        meetingType = request.meetingType
        title = request.title
        description = request.description
        beginAt = LocalDateTime.of(request.date, request.beginAt)
        endAt = LocalDateTime.of(request.date, request.endAt)
        meetingLocation = request.location
        outsideLocation = request.outsideLocation
    }

    fun hasAuthority(userId: String) = (creatorId == uuidFrom(userId))

    fun add(invitee: Invitee) {
        this.invitees += invitee
        _invitees.addNew(invitee)
    }

    fun add(invitees: List<Invitee>) {
        this.invitees += invitees
        _invitees.addNew(invitees)
    }

    fun exclude(invitee: Invitee) {
        this.invitees -= invitee
        _invitees.exclude(invitee)
    }

    fun exclude(invitees: List<Invitee>) {
        this.invitees -= invitees
        _invitees.exclude(invitees)
    }

    fun confirmedBy(userId: UUID) = _invitees.confirmedBy(userId)

    fun refusedBy(userId: UUID) = _invitees.refusedBy(userId)

    fun getInviteesBy(status: AttendanceDecisionStatus): List<Invitee> = getInviteesBy(status)
}
