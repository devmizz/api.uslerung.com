package com.userdomain.entity

import com.domain.global.entity.BaseEntity
import com.userdomain.dto.param.SlackUserDomainParam
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class SlackUser(
    slackId: String,
    displayName: String,
    realName: String,
    email: String,
    imageOrigin: String
) : BaseEntity() {

    @Column
    val slackId: String = slackId

    @Column
    var displayName: String = displayName

    @Column
    var realName: String = realName

    @Column
    var email: String = email

    @Column
    var imageOrigin: String = imageOrigin

    companion object {

        fun withUser(
            param: SlackUserDomainParam
        ) = SlackUser(
            slackId = param.slackId,
            displayName = param.displayName,
            realName = param.realName,
            email = param.email,
            imageOrigin = param.imageOrigin
        )
    }

    fun update(scrapSlackUser: SlackUser) {
        this.displayName = scrapSlackUser.displayName
        this.realName = scrapSlackUser.realName
        this.email = scrapSlackUser.email
        this.imageOrigin = scrapSlackUser.imageOrigin
    }

    fun hasUpdate(slackUser: SlackUser): Boolean = this.displayName != slackUser.displayName ||
        this.realName != slackUser.realName ||
        this.email != slackUser.email ||
        this.imageOrigin != slackUser.imageOrigin
}
