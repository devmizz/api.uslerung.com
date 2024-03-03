package com.slack.handler

import com.slack.api.bolt.App

interface SlackHandler {

    fun handle(app: App)
}
