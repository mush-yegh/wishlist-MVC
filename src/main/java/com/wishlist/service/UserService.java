package com.wishlist.service;

import com.wishlist.service.dto.UserDto;
import com.wishlist.persistance.entity.State;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.Status;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.entity.RequestEntity;
import org.springframework.security.core.Authentication;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.persistance.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    public List<UserDto> findAllActiveUsers(Authentication auth) {
        //get logged in user
        UserEntity loggedInUser = findLoggedInUser(auth);
        String loggedInUserMail = loggedInUser.getMail();

        List<UserEntity> userEntities = userRepository.findAllByState(State.ACTIVE);

        List<RequestEntity> sentRequests = requestRepository.findAllBySentRequestOwnerAndStatusIsNot(loggedInUser, Status.REJECTED);
        List<Long> senderIds = sentRequests.stream()
                .map(r -> r.getReceivedRequestOwner().getId())
                .collect(Collectors.toList());
        List<RequestEntity> receivedRequests = requestRepository.findAllByReceivedRequestOwnerAndStatusIsNot(loggedInUser, Status.REJECTED);
        List<Long> receiverIds = receivedRequests.stream()
                .map(r -> r.getSentRequestOwner().getId())
                .collect(Collectors.toList());

        //filter all users having requests by status ACCEPT or PENDING
        userEntities = userEntities.stream()
                .filter(u -> !u.getMail().equalsIgnoreCase(loggedInUserMail)
                        && !senderIds.contains(u.getId())
                        && !receiverIds.contains(u.getId())
                )
                .collect(Collectors.toList());

        return UserDto.mapEntityListToDto(userEntities);
    }

    public UserDto findUserById(Long id) {
        UserEntity userEntity = userRepository.findOneById(id);
        return UserDto.mapEntityToDto(userEntity);
    }

    public List<UserEntity> findAllUsers() {
        return userRepository.findAllByState(State.ACTIVE);
    }
}
