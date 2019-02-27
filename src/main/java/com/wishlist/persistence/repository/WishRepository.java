package com.wishlist.persistence.repository;

import com.wishlist.persistence.entity.UserEntity;
import com.wishlist.persistence.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishRepository extends JpaRepository<WishEntity, Long> {
    List<WishEntity> findAllByWishOwner(UserEntity owner);

    WishEntity findOneById(Long wishId);

    WishEntity findOneByIdAndWishOwner(Long id, UserEntity owner);
}
