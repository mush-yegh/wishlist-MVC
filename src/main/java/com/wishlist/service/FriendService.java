package com.wishlist.service;

import com.wishlist.service.dto.FriendDto;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.FriendEntity;
import org.springframework.security.core.Authentication;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.persistance.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.wishlist.service.dto.FriendDto.mapEntitiesToDtos;
import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@Service
public class FriendService {
    @Autowired
    private FriendRepository repository;

    public List<FriendDto> getFriends(Authentication auth) {
        Long loggedInUserId = findLoggedInUser(auth).getId();

        List<FriendEntity> friendEntities = repository.findAllByUserOneId(loggedInUserId);

        return mapEntitiesToDtos(friendEntities);
    }
}
