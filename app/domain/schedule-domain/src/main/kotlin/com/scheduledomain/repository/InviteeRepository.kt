package com.scheduledomain.repository

import com.scheduledomain.entity.Invitee
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface InviteeRepository : JpaRepository<Invitee, UUID>
