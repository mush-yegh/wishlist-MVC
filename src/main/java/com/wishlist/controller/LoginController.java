package com.wishlist.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLoginPage(Authentication auth, ModelMap model, HttpServletRequest request) {
        if (auth != null)
            return "redirect:/home";

        if (request.getParameterMap().containsKey("error")) {
            model.addAttribute("error", true);
        }

        return "login";
    }
}
