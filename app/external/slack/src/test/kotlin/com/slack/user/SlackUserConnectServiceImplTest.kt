package com.slack.user

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SlackUserConnectServiceImplTest(
    private val slackUserConnectServiceImpl: SlackUserServiceImpl
) : DescribeSpec({

    describe("SlackUserConnectServiceImpl") {

        context("유저 연동") {

            it("조회") {
                slackUserConnectServiceImpl.findUsersList()
            }
        }
    }
})
