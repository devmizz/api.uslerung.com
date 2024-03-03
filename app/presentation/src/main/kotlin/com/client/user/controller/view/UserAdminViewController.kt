package com.client.user.controller.view

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class UserAdminViewController {

    @GetMapping("/users/scrap")
    fun getScrapPage(): String {
        return "/user/scrap"
    }
}
