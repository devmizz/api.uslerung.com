package com.userdomain.service

import com.common.error.CustomException
import com.userdomain.entity.User
import com.userdomain.error.UserError
import com.userdomain.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserDomainService(
    private val userRepository: UserRepository
) {

    fun linkWithSlack(userId: UUID): User = userRepository.findByIdOrNull(userId)?.also { user ->
        if (user.slackUser == null) {
            throw CustomException(UserError.NOT_EXIST_IN_SLACK)
        }

        user.approveSlackLink()
    } ?: throw CustomException(UserError.LINK_FAIL_NO_USER)

    fun unlinkWithSlack(userId: UUID) = userRepository.findByIdOrNull(userId)?.also { it.refuseSlackLink() }
        ?: throw CustomException(UserError.UNLINK_FAIL_NO_USER)

    fun saveAll(users: List<User>): List<User> {
        return userRepository.saveAll(users)
    }

    fun findUsers(): List<User> = userRepository.findAll()

    fun findUsers(ids: List<UUID>): List<User> = try {
        userRepository.findAllById(ids)
    } catch (e: IllegalArgumentException) {
        throw CustomException(UserError.NOT_EXIST_IN_SLACK)
    }
}
