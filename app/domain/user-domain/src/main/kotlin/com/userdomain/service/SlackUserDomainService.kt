package com.userdomain.service

import com.userdomain.entity.SlackUser
import com.userdomain.repository.SlackUserRepository
import org.springframework.stereotype.Service

@Service
class SlackUserDomainService(
    private val slackUserRepository: SlackUserRepository
) {

    fun saveAll(slackUsers: List<SlackUser>): List<SlackUser> {
        return slackUserRepository.saveAll(slackUsers)
    }

    fun findAll(): List<SlackUser> {
        return slackUserRepository.findAll()
    }

    fun deleteAll(entities: List<SlackUser>) {
        slackUserRepository.deleteAll(entities)
    }
}
