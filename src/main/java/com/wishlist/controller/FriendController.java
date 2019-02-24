package com.wishlist.controller;

import com.wishlist.service.FriendService;
import com.wishlist.service.dto.FriendDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping(value = "/friends")
public class FriendController {
    @Autowired
    private FriendService service;

    @GetMapping
    public ResponseEntity<?> getFriends(Authentication auth) {

        List<FriendDto> friends = service.getFriends(auth);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }
}
