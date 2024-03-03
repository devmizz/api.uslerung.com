package com.userdomain.repository

import com.userdomain.entity.SlackUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SlackUserRepository : JpaRepository<SlackUser, UUID>
