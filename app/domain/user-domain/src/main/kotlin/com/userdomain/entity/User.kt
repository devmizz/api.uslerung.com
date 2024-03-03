package com.userdomain.entity

import com.common.error.CustomException
import com.common.isFalseThen
import com.common.isTrueThen
import com.domain.global.entity.BaseEntity
import com.userdomain.error.UserError
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "users")
class User(
    slackUser: SlackUser
) : BaseEntity() {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "slack_user_id")
    val slackUser: SlackUser = slackUser

    var hasSlackLinkApproval: Boolean = false
        protected set

    companion object {
        fun with(slackUser: SlackUser) = User(slackUser)
    }

    fun approveSlackLink() {
        hasSlackLinkApproval.isTrueThen {
            throw CustomException(UserError.ALREADY_LINKED_USER)
        }

        if (slackUser == null) {
            throw CustomException(UserError.NOT_EXIST_IN_SLACK)
        }

        hasSlackLinkApproval = true
    }

    fun refuseSlackLink() {
        hasSlackLinkApproval.isFalseThen {
            throw CustomException(UserError.UNLINKED_USER)
        }

        hasSlackLinkApproval = false
    }
}
