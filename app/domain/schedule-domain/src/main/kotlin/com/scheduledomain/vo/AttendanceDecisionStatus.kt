package com.scheduledomain.vo

import com.domain.global.vo.DomainLayerVO

enum class AttendanceDecisionStatus : DomainLayerVO {
    PENDING, CONFIRMED, REFUSED
}
