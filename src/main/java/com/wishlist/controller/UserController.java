package com.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/users")
    public String getUsersPage(ModelMap model){
        model.addAttribute("username", "Mushegh");
        return "users";
    }
}
