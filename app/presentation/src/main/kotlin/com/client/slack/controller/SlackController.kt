package com.client.slack.controller

import com.slack.api.bolt.App
import com.slack.api.bolt.jakarta_servlet.SlackAppServlet
import jakarta.servlet.annotation.WebServlet

@WebServlet("/slack/events")
class SlackController(
    app: App
) : SlackAppServlet(app)
