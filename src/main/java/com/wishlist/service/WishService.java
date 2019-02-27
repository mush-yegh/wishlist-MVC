package com.wishlist.service;

import com.wishlist.service.dto.WishDto;
import org.springframework.stereotype.Service;
import com.wishlist.persistence.entity.UserEntity;
import com.wishlist.persistence.entity.WishEntity;
import org.springframework.security.core.Authentication;
import com.wishlist.persistence.repository.WishRepository;
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
        List<WishEntity> userWishList = repository.findAllByWishOwner(loggedInUser);
        return WishDto.mapEntityListToDtoList(userWishList);
    }

    public Optional<WishDto> createWish(Authentication auth, WishDto wishDto) {
        UserEntity loggedInUser = findLoggedInUser(auth);
        WishEntity wishEntity = WishDto.mapDtoToEntity(wishDto);
        wishEntity.setWishOwner(loggedInUser);
        wishEntity.setCreated(LocalDate.now());
        WishEntity savedWish = repository.save(wishEntity);
        return Optional.of(WishDto.mapEntityToDto(savedWish));
    }

    public Optional<WishDto> getWishById(Long wishId) {
        WishEntity wishEntity = repository.findOneById(wishId);
        return Optional.of(WishDto.mapEntityToDto(wishEntity));
    }

    public Optional<WishDto> updateWish(Authentication auth, WishDto wishDto) {
        UserEntity loggedInUser = findLoggedInUser(auth);
        Optional<WishEntity> wishEntity = Optional.of(repository.findOneByIdAndWishOwner(wishDto.getId(),loggedInUser));
        if (!wishEntity.isPresent())
            return Optional.empty();

        WishEntity wishToUpdate = wishEntity.get();
        wishToUpdate.setTitle(wishDto.getTitle());
        wishToUpdate.setLink(wishDto.getLink());
        wishToUpdate.setDescription(wishDto.getDescription());
        wishToUpdate.setUpdated(LocalDate.now());

        WishEntity updatedWish = repository.save(wishToUpdate);
        return Optional.of(WishDto.mapEntityToDto(updatedWish));
    }

    public Boolean deleteWishById(Authentication auth, Long wishId) {
        UserEntity loggedInUser = findLoggedInUser(auth);
        Optional<WishEntity> wishEntity = Optional.of(repository.findOneByIdAndWishOwner(wishId,loggedInUser));
        if (wishEntity.isPresent()) {
            repository.delete(wishEntity.get());
            return true;
        }
        return false;
    }
}
