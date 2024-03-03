package com.client.user.controller

import com.client.user.dto.request.UserLinkInvitationPresentationRequest
import com.client.user.dto.response.SimpleUserPresentationResponse
import com.userapplication.user.service.UserApplicationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저")
@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userApplicationService: UserApplicationService
) {

    @Tag(name = "유저")
    @Operation(
        summary = "전체 유저 조회",
        description = "가장 단순한 형태로 전체 유저 목록 조회"
    )
    @GetMapping("/simple")
    fun getUsers(): ResponseEntity<List<SimpleUserPresentationResponse>> {
        return ResponseEntity.ok(
            userApplicationService.getUsers().map {
                SimpleUserPresentationResponse.from(it)
            }
        )
    }

    @Tag(name = "유저")
    @Operation(
        summary = "유저 연동 초대",
        description = "이용하지 않는 유저를 대상으로 어슬렁 서비스를 이용하도록 초대"
    )
    @PostMapping("/invite")
    fun inviteUser(request: UserLinkInvitationPresentationRequest) {
        userApplicationService.inviteUserToLinkSlack(request.toApplicationRequest())
    }
}
