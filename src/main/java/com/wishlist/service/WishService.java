package com.wishlist.service;

import com.wishlist.service.dto.WishDto;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.entity.WishEntity;
import org.springframework.security.core.Authentication;
import com.wishlist.persistance.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@Service
public class WishService {
    @Autowired
    private WishRepository repository;

    public List<WishDto> findUserWishList(Authentication auth){
        UserEntity loggedInUser = findLoggedInUser(auth);
        List<WishEntity> userWishlist = repository.findAllByWishOwner(loggedInUser);
        return WishDto.mapEntityListToDtoList(userWishlist);
    }
}
