package com.wishlist.service;

import com.wishlist.persistance.entity.FriendEntity;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.FriendRepository;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.service.dto.FriendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@Service
public class FriendService {
    @Autowired
    private FriendRepository repository;
    @Autowired
    private UserRepository userRepository;

    public List<FriendDto> getFriends(Authentication auth) {
        Long loggedInUserId = findLoggedInUser(auth).getId();

        List<FriendEntity> friendEntities = repository.findAllByUserOneId(loggedInUserId);

        return FriendDto.mapEntitiesToDtos(friendEntities);
    }
}
