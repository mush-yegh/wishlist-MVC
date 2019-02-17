package com.wishlist.controller;

import com.wishlist.service.dto.UserDto;
import com.wishlist.service.UserService;
import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.http.HttpStatus;
import com.wishlist.persistance.entity.Status;
import org.springframework.http.ResponseEntity;
import com.wishlist.service.dto.SocketResponseDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.time.LocalDate;
import java.util.Optional;

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

        requestDto.setRequestDate(LocalDate.now());
        requestDto.setStatus(String.valueOf(Status.PENDING));
        requestService.saveRequest(requestDto);

        UserDto sender = userService.findUserById(senderId);
        SocketResponseDto resp = SocketResponseDto.builder()
                .notifType("friendRequest")
                .recipientId(recipientId)
                .sender(sender)
                .build();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/requests/reject")
    public ResponseEntity<?> rejectFriendRequest(@RequestBody RequestDto req) {
        Optional<RequestDto> requestDto = requestService.rejectRequest(Long.parseLong(req.getRecipientId()),
                Long.parseLong(req.getSenderId()));
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping("/requests/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody RequestDto req) {
        Optional<RequestDto> requestDto = requestService.acceptRequest(Long.parseLong(req.getRecipientId()),
                Long.parseLong(req.getSenderId()));
        return new ResponseEntity<>(requestDto, HttpStatus.OK);
        //return ;

    }
}
