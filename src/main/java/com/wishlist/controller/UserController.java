package com.wishlist.controller;

import com.wishlist.service.UserService;
import com.wishlist.service.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    private UserService service;


    //@CrossOrigin(origins = "http://localhost:8080")
    //@CrossOrigin(origins = "http://192.168.7.53")

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<UserDto>> getActiveUsers(Authentication auth) {
        List<UserDto> users = service.findAllActiveUsers(auth);
        return new ResponseEntity<>(users, HttpStatus.OK);
        //return ResponseEntity.ok(users);
    }

}
