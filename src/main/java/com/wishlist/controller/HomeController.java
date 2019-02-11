package com.wishlist.controller;

import com.wishlist.security.details.UserDetailsImpl;
import com.wishlist.service.dto.UserDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import static com.wishlist.service.dto.UserDto.mapEntityToDto;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String getHomePage(ModelMap model, Authentication auth){
        if (auth == null)
            return "redirect:/login";
        UserDetailsImpl details = (UserDetailsImpl)auth.getPrincipal();
        UserDto userDto = mapEntityToDto(details.getUserEntity());
        model.addAttribute("user", userDto);
        return "home";
    }
}
