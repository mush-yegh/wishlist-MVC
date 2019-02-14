package com.wishlist.controller;

import com.wishlist.service.dto.SocketResponseDto;
import com.wishlist.service.dto.UserDto;
import com.wishlist.service.UserService;
import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RestController
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @MessageMapping("/srvSocket")
    @SendTo("/client/notification")
    public ResponseEntity<?> sendFriendRequest(@RequestBody RequestDto requestDto) throws Exception {
        Long senderId = Long.parseLong(requestDto.getSenderId());
        Long recipientId = Long.parseLong(requestDto.getRecipientId());

            System.out.println("senderId = " + senderId);
            System.out.println("recipientId = " + recipientId);

        //TO DO -save request to db

        UserDto sender = userService.findUserById(senderId);
        SocketResponseDto resp = SocketResponseDto.builder()
                .recipientId(recipientId)
                .sender(sender)
                .build();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
