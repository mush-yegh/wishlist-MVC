package com.wishlist.persistance.repository;

import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<WishEntity, Long> {
    List<WishEntity> findAllByWishOwner(UserEntity owner);

    WishEntity findOneById(Long wishId);

    WishEntity findOneByIdAndWishOwner(Long id, UserEntity owner);
}
