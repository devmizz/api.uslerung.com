package com.scheduledomain.repository

import com.scheduledomain.entity.Meeting
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MeetingRepository : JpaRepository<Meeting, UUID>
