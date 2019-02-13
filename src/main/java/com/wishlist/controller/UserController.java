package com.wishlist.controller;

import com.wishlist.service.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.Null;
import java.util.List;

@Controller
public class UserController {

    @CrossOrigin(origins = "http://localhost:8080")
    @ResponseBody
    @GetMapping("/users")
    public String getUserList(ModelMap model){
        //model.addAttribute("username", "Mushegh");
        return "users";
    }
}
