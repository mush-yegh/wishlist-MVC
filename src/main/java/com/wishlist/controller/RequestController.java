package com.wishlist.controller;

import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.security.details.UserDetailsImpl;
import com.wishlist.service.dto.UserDto;
import com.wishlist.service.UserService;
import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.http.HttpStatus;
import com.wishlist.persistance.entity.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.wishlist.service.dto.SocketResponseDto;
import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.wishlist.service.dto.UserDto.mapEntityToDto;

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

        //TO DO check if loggedInUser can send request to receiver IMPORTANT
        requestService.saveRequest(requestDto);

        UserDto sender = userService.findUserById(senderId);
        SocketResponseDto resp = SocketResponseDto.builder()
                .notifType("friendRequest")
                .recipientId(recipientId)
                .sender(sender)
                .build();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/requests/reject", consumes = "text/plain")
    public ResponseEntity<?> rejectFriendRequest(@RequestBody String id, Authentication auth) {
        Long senderId;
        try {
            senderId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            System.out.println("numFormat exception");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        Optional<?> response = requestService.rejectRequest(auth, senderId);
        if (response.isPresent()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("Recipient not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/requests/accept", consumes = "text/plain")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody String id, Authentication auth) {
        Long senderId;
        try {
            senderId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            System.out.println("numFormat exception");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        Optional<?> response = requestService.acceptRequest(auth, senderId);

        if (response.isPresent()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Recipient not found", HttpStatus.NOT_FOUND);
        //correct HTTP.STATUS for this case ^ - ???
    }

    @GetMapping("/requests/sentRequests")
    public ResponseEntity<?> getSentRequests(Authentication auth) {
        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
        Long loggedInUserId = details.getUserEntity().getId();
        Optional<List<RequestEntity>> sentRequests = requestService.findUserSentRequests(loggedInUserId);

        return new ResponseEntity<>(sentRequests, HttpStatus.OK);
    }

    @GetMapping("/requests/receivedRequests")
    public ResponseEntity<?> getReceivedRequests(Authentication auth) {
        UserDetailsImpl details = (UserDetailsImpl) auth.getPrincipal();
        Long loggedInUserId = details.getUserEntity().getId();
        Optional<List<RequestDto>> receivedRequests = requestService.findUserReceivedRequests(loggedInUserId);

        return new ResponseEntity<>(receivedRequests, HttpStatus.OK);
    }
}
