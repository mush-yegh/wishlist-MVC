package com.wishlist.controller;


import com.wishlist.service.WishService;
import com.wishlist.service.dto.WishDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/wishes")
public class WishController {
    @Autowired
    private WishService service;

    @GetMapping
    public ResponseEntity<?> getUserWishList(Authentication auth) {
        List<WishDto> wishes = service.findUserWishList(auth);
        return new ResponseEntity<>(wishes, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createWish(Authentication auth, @RequestBody WishDto wishDto) {
        Optional<WishDto> wish = service.createWish(auth, wishDto);
        if (wish.isPresent())
            return new ResponseEntity<>(wish, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.CONFLICT);//or HttpStatus.FAILED_DEPENDENCY
    }

}
