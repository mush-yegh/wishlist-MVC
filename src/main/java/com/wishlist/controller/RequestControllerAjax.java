package com.wishlist.controller;

import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.service.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;

@RestController
@RequestMapping("app/requests")
public class RequestControllerAjax {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @PostMapping("/sendRequest")
    public ResponseEntity<?> sendFriendRequest(@RequestBody RequestDto requestDto) throws Exception {
        Long userFromId = Long.parseLong(requestDto.getUserFromId());
        Long userToId = Long.parseLong(requestDto.getUserToId());
        System.out.println("userFromId = " + userFromId);
        System.out.println("userToId = " + userToId);

        UserEntity userEntityTo = userRepository.findOneById(userToId);

        simpMessagingTemplate.convertAndSendToUser(
                userEntityTo.getMail(),
                "/topic/greetings",
                "incoming frienfRequest"
        );

        return new ResponseEntity<>("send", HttpStatus.OK);
    }
}
