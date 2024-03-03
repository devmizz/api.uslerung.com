package com.client.schedule.controller

import com.client.schedule.dto.request.MeetingCreationPresentationRequest
import com.client.schedule.dto.request.MeetingUpdatePresentationRequest
import com.client.schedule.dto.response.MeetingDuplicatePresentationResponse
import com.client.schedule.dto.response.MeetingPresentationResponse
import com.client.schedule.dto.response.MultipleMeetingPresentationResponse
import com.client.schedule.vo.MeetingOfflineLocationInPresentation
import com.client.security.dto.SecurityUser
import com.common.parseForDate
import com.common.parseForMinute
import com.scheduleapplication.service.MeetingApplicationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회의")
@RestController
@RequestMapping("/v1/meetings")
class MeetingController(
    private val meetingApplicationService: MeetingApplicationService
) {

    @Tag(name = "회의")
    @Operation(summary = "회의 생성")
    @PostMapping
    fun createMeeting(
        @RequestBody request: MeetingCreationPresentationRequest,
        @AuthenticationPrincipal securityUser: SecurityUser
    ): ResponseEntity<MeetingPresentationResponse> {
        return ResponseEntity.ok(
            MeetingPresentationResponse.from(
                meetingApplicationService.createMeeting(
                    request.toApplicationLayer(),
                    securityUser.userId
                )
            )
        )
    }

    @Tag(name = "회의")
    @Operation(summary = "회의 수정")
    @PutMapping
    fun updateMeeting(
        @RequestBody request: MeetingUpdatePresentationRequest,
        @AuthenticationPrincipal securityUser: SecurityUser
    ): ResponseEntity<MeetingPresentationResponse> {
        return ResponseEntity.ok(
            MeetingPresentationResponse.from(
                meetingApplicationService.updateMeeting(
                    request.toApplicationLayer(),
                    securityUser.userId
                )
            )
        )
    }

    @Tag(name = "회의")
    @Operation(summary = "회의 조회")
    @GetMapping("/{id}")
    fun findMeeting(
        @PathVariable id: String
    ): ResponseEntity<MeetingPresentationResponse> {
        return ResponseEntity.ok(
            MeetingPresentationResponse.from(meetingApplicationService.findMeeting(id))
        )
    }

    @Tag(name = "회의")
    @Operation(summary = "회의 범위 조회")
    @Parameters(
        Parameter(name = "from", example = "2024-02-12"),
        Parameter(name = "to", example = "2024-02-13")
    )
    @GetMapping
    fun findMeetings(
        @RequestParam(required = true) from: String,
        @RequestParam(required = true) to: String
    ): ResponseEntity<MultipleMeetingPresentationResponse> {
        return ResponseEntity.ok(
            MultipleMeetingPresentationResponse.from(
                meetingApplicationService.findMeetings(from.parseForDate(), to.parseForDate())
            )
        )
    }

    @Tag(name = "회의")
    @Operation(summary = "오프라인 회의 장소 중 시간이 겹치는 회의가 있는지 확인")
    @Parameters(
        Parameter(name = "from", example = "2024-02-12 01:00"),
        Parameter(name = "to", example = "2024-02-13 03:00")
    )
    @GetMapping("/duplicate")
    fun hasSameTimeMeetingInOfflineLocation(
        @Parameter(schema = Schema(format = "yyyy-MM-dd HH:mm"))
        @RequestParam(required = true)
        from: String,
        @Parameter(schema = Schema(format = "yyyy-MM-dd HH:mm"))
        @RequestParam(required = true)
        to: String,
        @RequestParam(required = true) location: MeetingOfflineLocationInPresentation
    ): ResponseEntity<MeetingDuplicatePresentationResponse> {
        return ResponseEntity.ok(
            MeetingDuplicatePresentationResponse.from(
                meetingApplicationService.hasDuplicateMeetingInOfflineLocation(
                    from.parseForMinute(),
                    to.parseForMinute(),
                    location.toApplicationLayer()
                )
            )
        )
    }

    @Tag(name = "회의")
    @Operation(summary = "회의 삭제")
    @DeleteMapping("/{id}")
    fun deleteMeeting(
        @PathVariable id: String,
        @AuthenticationPrincipal securityUser: SecurityUser
    ) {
        meetingApplicationService.deleteMeeting(id, securityUser.userId)
    }
}
