package com.wishlist.persistance.repository;

import com.wishlist.persistance.entity.FriendEntity;
import com.wishlist.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findAllByUserOneId(Long ID);
}
