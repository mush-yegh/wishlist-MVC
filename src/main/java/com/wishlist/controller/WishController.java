package com.wishlist.controller;


import com.wishlist.service.WishService;
import com.wishlist.service.dto.WishDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/wishes")
public class WishController {
    @Autowired
    private WishService service;

    @GetMapping
    public ResponseEntity<?> getActiveUsers(Authentication auth) {
        List<WishDto> wishes = service.findUserWishList(auth);
        return new ResponseEntity<>(wishes, HttpStatus.OK);
    }

}
