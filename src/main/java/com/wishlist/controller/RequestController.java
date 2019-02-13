package com.wishlist.controller;

import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @PostMapping("/sendRequest")
    public ResponseEntity<?> sendFriendRequest(@RequestBody RequestDto requestDto){
        Long userFromId = Long.parseLong(requestDto.getUserFromId());
        Long userToId = Long.parseLong(requestDto.getUserToId());
        System.out.println("userFromId = " + userFromId);
        System.out.println("userToId = " + userToId);

        //RequestDto savedRequest = requestService.saveRequest(requestDto);
        requestService.saveRequest(requestDto);

        return new ResponseEntity<>("Saved",HttpStatus.OK);
    }
}
