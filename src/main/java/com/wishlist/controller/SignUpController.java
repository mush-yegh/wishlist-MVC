package com.wishlist.controller;

import com.wishlist.service.dto.UserDto;
import com.wishlist.service.SignUpService;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SignUpController {
    @Autowired
    SignUpService service;

    @GetMapping(value = {"/signUp","/"})
    public String getSignUpPage(Authentication auth){
        if (auth != null)
            return "redirect:/home";

        return "signUp";
    }

    @PostMapping("/signUp")
    public String signUp(UserDto userDto){
        service.signUp(userDto);
        return "redirect:/login";
    }
}
