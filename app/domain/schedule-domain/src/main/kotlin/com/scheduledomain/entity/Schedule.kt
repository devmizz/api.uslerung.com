package com.scheduledomain.entity

import com.domain.global.entity.BaseEntity
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
abstract class Schedule : BaseEntity() {

    abstract val creatorId: UUID

    abstract var beginAt: LocalDateTime
        protected set

    abstract var endAt: LocalDateTime
        protected set
}
