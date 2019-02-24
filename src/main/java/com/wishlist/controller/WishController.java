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

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUserWishById(Authentication auth, @PathVariable String id){
        Long wishId;
        try {
            wishId = Long.parseLong(id);
        }catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<WishDto> wishToEdit = service.getWishById(wishId);
        if (wishToEdit.isPresent()){
            return new ResponseEntity<>(wishToEdit, HttpStatus.OK);
        }
        return new ResponseEntity<>(Optional.empty(), HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<?> createWish(Authentication auth, @RequestBody WishDto wishDto) {
        Optional<WishDto> wish = service.createWish(auth, wishDto);
        if (wish.isPresent())
            return new ResponseEntity<>(wish, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @PutMapping
    public ResponseEntity<?> updateWish(Authentication auth, @RequestBody WishDto wishDto){

        Optional<WishDto> wish = service.updateWish(auth, wishDto);
        if (wish.isPresent())
            return new ResponseEntity<>(wish, HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteWishById(Authentication auth, @PathVariable String id){
        Long wishId;
        try {
            wishId = Long.parseLong(id);
        }catch (NumberFormatException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Boolean isDeleted  = service.deleteWishById(auth, wishId);
        if (isDeleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
