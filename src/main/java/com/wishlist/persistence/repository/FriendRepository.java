package com.wishlist.persistence.repository;

import com.wishlist.persistence.entity.FriendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<FriendEntity, Long> {
    List<FriendEntity> findAllByUserOneId(Long ID);
}
