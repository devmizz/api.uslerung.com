package com.userapplication.slack.service

import com.domain.global.transaction.TransactionAdvisor
import com.domain.global.transaction.TransactionHandler
import com.userapplication.slack.dto.param.SlackUserServiceParam
import com.userapplication.support.fixture.createSlackUserServiceParam
import com.userdomain.service.SlackUserDomainService
import com.userdomain.service.UserDomainService
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class UserScrapServiceTest : DescribeSpec({

    lateinit var scrapSlackUsers: List<SlackUserServiceParam>

    beforeSpec {
        TransactionHandler(TransactionAdvisor())

        scrapSlackUsers = List(3) { index ->
            createSlackUserServiceParam(
                slackId = "slackId-$index",
                teamId = "teamId-$index",
                displayName = "displayName-$index",
                realName = "realName-$index",
                email = "email-$index"
            )
        }
    }

    val slackUserService = mockk<SlackUserService>()
    val userDomainService = mockk<UserDomainService>()
    val slackUserDomainService = mockk<SlackUserDomainService>()
    val userScrapService = UserScrapService(slackUserService, userDomainService, slackUserDomainService)

    describe("UserScrapService") {

        context("모두 신규 유저인 경우") {

            every { slackUserService.findUsersList() } returns scrapSlackUsers
            every { slackUserDomainService.findAll() } returns emptyList()
            every { slackUserDomainService.saveAll(any()) } returns emptyList()
            every { userDomainService.saveAll(any()) } returns emptyList()

            it("저장 메서드만 호출된다.") {
                userScrapService.scrapSlackUsers()

                verify(exactly = 1) {
                    userDomainService.saveAll(any())
                    slackUserDomainService.saveAll(any())
                }
                verify(exactly = 0) {
                    slackUserDomainService.deleteAll(any())
                }
            }
        }
    }
})
