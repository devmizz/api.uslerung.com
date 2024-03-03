package com.client.user.controller

import com.client.user.dto.request.SlackLinkPresentationRequest
import com.client.user.dto.request.SlackUnlinkPresentationRequest
import com.userapplication.slack.service.UserLinkService
import com.userapplication.slack.service.UserScrapService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "슬랙")
@RestController
@RequestMapping("/v1/slack")
class UserSlackController(
    private val userScrapService: UserScrapService,
    private val userLinkService: UserLinkService
) {

    @Tag(name = "슬랙")
    @Operation(
        summary = "유저 동기화",
        description = "슬랙 채널 유저와 DB를 동기화"
    )
    @PostMapping("/users")
    fun scrapSlackUsers() {
        userScrapService.scrapSlackUsers()
    }

    @Tag(name = "슬랙")
    @Operation(
        summary = "유저 연동",
        description = "유저가 연동에 동의"
    )
    @PostMapping("/users/link")
    fun linkSlackUser(
        @RequestBody request: SlackLinkPresentationRequest
    ) {
        userLinkService.linkUser(request.toApplicationLayer())
    }

    @Tag(name = "슬랙")
    @Operation(
        summary = "유저 연동 해제",
        description = "기존 연동을 해제"
    )
    @PostMapping("/users/unlink")
    fun unlinkSlackUser(
        @RequestBody request: SlackUnlinkPresentationRequest
    ) {
        userLinkService.unlinkUser(request.toApplicationLayer())
    }
}
