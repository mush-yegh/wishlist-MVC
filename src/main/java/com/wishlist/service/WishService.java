package com.wishlist.service;

import com.wishlist.service.dto.WishDto;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.entity.WishEntity;
import org.springframework.security.core.Authentication;
import com.wishlist.persistance.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<WishDto> createWish(Authentication auth, WishDto wishDto) {
        UserEntity loggedInUser = findLoggedInUser(auth);
        WishEntity wishEntity = WishDto.mapDtoToEntity(wishDto);
        wishEntity.setWishOwner(loggedInUser);
        wishEntity.setCreated(LocalDate.now());
        WishEntity savedWish = repository.save(wishEntity);
        return Optional.of(WishDto.mapEntityToDto(savedWish));
    }
}
