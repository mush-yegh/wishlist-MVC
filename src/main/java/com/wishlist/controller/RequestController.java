package com.wishlist.controller;

import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;


@RestController
//@RequestMapping("/requests")
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserRepository userRepository;

    //@PostMapping("/sendRequest")
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseEntity<?> sendFriendRequest(@RequestBody RequestDto requestDto) throws Exception {
        Long userFromId = Long.parseLong(requestDto.getUserFromId());
        Long userToId = Long.parseLong(requestDto.getUserToId());
        System.out.println("userFromId = " + userFromId);
        System.out.println("userToId = " + userToId);

        //RequestDto savedRequest = requestService.saveRequest(requestDto);

        //requestService.saveRequest(requestDto);

        //simpMessagingTemplate.convertAndSendToUser(user, "/notify",Map<String,Object>);
        /*User loggedInUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();*/
        //UserEntity loggedInUser = userRepository.findOneById(userFromId);

        UserEntity userEntityTo = userRepository.findOneById(userToId);


        //org.springframework.security.core.userdetails.User userTo =  sessionRegistry.getAllPrincipals()
//                .stream()
//                .map(p -> (User) p)
//                .filter(user -> user.getUsername().equals(userEntityTo.getMail()))
//
//                .findFirst()
//                .get();
        /*Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();*/
/*
        simpMessagingTemplate.convertAndSendToUser(
                userEntityTo.getMail(),
                "/notify",
                "frienfRequest"
//                MessageFormat.format("{0}: {1}",
//                        loggedInUser, "friendRequest"
//                )
           // return new ResponseEntity<>("",HttpStatus.OK);
        );*/


        return new ResponseEntity<>("Saved", HttpStatus.OK);
    }
}
