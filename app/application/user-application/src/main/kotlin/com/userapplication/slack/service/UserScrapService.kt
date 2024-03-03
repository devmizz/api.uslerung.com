package com.userapplication.slack.service

import com.common.isTrueThen
import com.domain.global.transaction.TransactionHandler
import com.userapplication.slack.dto.param.SlackUserServiceParam
import com.userdomain.entity.SlackUser
import com.userdomain.entity.User
import com.userdomain.service.SlackUserDomainService
import com.userdomain.service.UserDomainService
import org.springframework.stereotype.Service

@Service
class UserScrapService(
    private val slackUserService: SlackUserService,
    private val userDomainService: UserDomainService,
    private val slackUserDomainService: SlackUserDomainService
) {

    fun scrapSlackUsers(): Unit = TransactionHandler.runWithReturn {
        val slackUserParams = slackUserService.findUsersList()

        val scrapSlackUserBySlackId = convertToUserAndSlackUser(slackUserParams).associateBy { it.slackId }
        val findSlackUserBySlackId = slackUserDomainService.findAll().associateBy { it.slackId }

        saveNewSlackUsers(
            scrapSlackUserBySlackId = scrapSlackUserBySlackId,
            findSlackUserBySlackId = findSlackUserBySlackId
        )

        deleteSlackUser(
            scrapSlackUserBySlackId = scrapSlackUserBySlackId,
            findSlackUserBySlackId = findSlackUserBySlackId
        )

        updateSlackUsers(
            scrapSlackUserBySlackId = scrapSlackUserBySlackId,
            findSlackUserBySlackId = findSlackUserBySlackId
        )
    }

    private fun convertToUserAndSlackUser(slackUserParams: List<SlackUserServiceParam>): List<SlackUser> {
        return slackUserParams.map { param ->
            SlackUser.withUser(param.toDomainParam())
        }
    }

    private fun saveNewSlackUsers(
        scrapSlackUserBySlackId: Map<String, SlackUser>,
        findSlackUserBySlackId: Map<String, SlackUser>
    ) {
        val newSlackUserIds = scrapSlackUserBySlackId.keys.subtract(findSlackUserBySlackId.keys)
        val newSlackUsers = newSlackUserIds.mapNotNull(scrapSlackUserBySlackId::get)

        newSlackUserIds.isEmpty().isTrueThen { return }

        slackUserDomainService.saveAll(newSlackUsers)
        userDomainService.saveAll(newSlackUsers.map { User.with(it) })
    }

    private fun deleteSlackUser(
        scrapSlackUserBySlackId: Map<String, SlackUser>,
        findSlackUserBySlackId: Map<String, SlackUser>
    ) {
        val slackUserIdsToDelete = findSlackUserBySlackId.keys.subtract(scrapSlackUserBySlackId.keys)
        val slackUsersToDelete = slackUserIdsToDelete.mapNotNull(findSlackUserBySlackId::get)

        slackUserIdsToDelete.isEmpty().isTrueThen { return }

        slackUserDomainService.deleteAll(slackUsersToDelete)
    }

    private fun updateSlackUsers(
        scrapSlackUserBySlackId: Map<String, SlackUser>,
        findSlackUserBySlackId: Map<String, SlackUser>
    ) {
        val targetOfUpdate = findSlackUserBySlackId.filter { (scrapSlackId, scrapSlackUser) ->
            scrapSlackUserBySlackId[scrapSlackId]?.hasUpdate(scrapSlackUser) == true
        }.values.toList()

        targetOfUpdate.isEmpty().isTrueThen { return }

        targetOfUpdate.forEach { targetSlackUser ->
            scrapSlackUserBySlackId[targetSlackUser.slackId]?.let { scrapSlackUser ->
                targetSlackUser.update(scrapSlackUser)
            }
        }
    }
}
