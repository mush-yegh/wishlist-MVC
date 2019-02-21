package com.wishlist.persistance.repository;

import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.persistance.entity.Status;
import com.wishlist.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    Optional<RequestEntity> findOneBySentRequestOwnerAndReceivedRequestOwnerAndStatus(UserEntity r, UserEntity s, Status status);

    List<RequestEntity> findAllBySentRequestOwner(UserEntity loggedInUser);
    List<RequestEntity> findAllByReceivedRequestOwner(UserEntity loggedInUser);
}
