package com.wishlist.controller;

import com.wishlist.service.UserService;
import com.wishlist.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService service;

    @CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserDto>> getActiveUsers() {
        List<UserDto> users = service.findAllActiveUsers();
        //return new ResponseEntity<>(users, HttpStatus.OK);
        return ResponseEntity.ok(users);
    }

    /*@CrossOrigin(origins = "http://localhost:8080")
    @ResponseBody
    @GetMapping("/users")
    public String getUserList(ModelMap model){
        //model.addAttribute("username", "Mushegh");
        return "users";
    }*/
}
