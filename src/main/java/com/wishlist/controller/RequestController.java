package com.wishlist.controller;

import com.wishlist.service.dto.UserDto;
import com.wishlist.service.UserService;
import com.wishlist.service.RequestService;
import com.wishlist.service.dto.RequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wishlist.service.dto.SocketResponseDto;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.MessageMapping;

import java.util.List;
import java.util.Optional;

import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@RestController
public class RequestController {
    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @MessageMapping("/srvSocket")
    @SendTo("/client/notification")
    public ResponseEntity<?> sendFriendRequest(@RequestBody String rId, Authentication auth) throws Exception {

        Long recipientId = Long.parseLong(rId);
        //TO DO check if loggedInUser can send request to receiver IMPORTANT
        requestService.saveRequest(auth, recipientId);

        UserDto sender = userService.findUserById(findLoggedInUser(auth).getId());
        SocketResponseDto resp = SocketResponseDto.builder()
                .notifType("friendRequest")
                .recipientId(recipientId)
                .sender(sender)
                .build();

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(value = "/requests/reject")
    public ResponseEntity<?> rejectFriendRequest(@RequestBody Long senderId, Authentication auth) {

        Optional<?> response = requestService.rejectRequest(auth, senderId);
        if (response.isPresent()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("Recipient not found", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/requests/accept")
    public ResponseEntity<?> acceptFriendRequest(@RequestBody Long senderId, Authentication auth) {

        Optional<?> response = requestService.acceptRequest(auth, senderId);

        if (response.isPresent()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>("Recipient not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/requests/sentRequests")
    public ResponseEntity<?> getSentRequests(Authentication auth) {

        List<RequestDto> sentRequests = requestService.findUserSentRequests(auth);
        if (sentRequests.size() > 0)
            return new ResponseEntity<>(sentRequests, HttpStatus.OK);
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/requests/receivedRequests")
    public ResponseEntity<?> getReceivedRequests(Authentication auth) {
        List<RequestDto> receivedRequests = requestService.findUserReceivedRequests(auth);
        if (receivedRequests.size() > 0)
            return new ResponseEntity<>(receivedRequests, HttpStatus.OK);
        return new ResponseEntity<>(Optional.empty(), HttpStatus.NO_CONTENT);
    }


}
