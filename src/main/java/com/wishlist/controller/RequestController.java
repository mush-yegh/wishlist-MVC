package com.wishlist.controller;

import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {
    @Autowired
    private RequestService requestService;


    @Autowired
    private UserRepository userRepository;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseEntity<?> sendFriendRequest(@RequestBody RequestDto requestDto) throws Exception {
        Long userFromId = Long.parseLong(requestDto.getUserFromId());
        Long userToId = Long.parseLong(requestDto.getUserToId());
        System.out.println("userFromId = " + userFromId);
        System.out.println("userToId = " + userToId);

        UserEntity userEntityTo = userRepository.findOneById(userToId);

        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }
}
