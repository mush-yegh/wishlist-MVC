package com.wishlist.persistence.repository;

import com.wishlist.persistence.entity.RequestEntity;
import com.wishlist.persistence.entity.Status;
import com.wishlist.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface RequestRepository extends JpaRepository<RequestEntity, Long> {

    Optional<RequestEntity> findOneBySentRequestOwnerAndReceivedRequestOwnerAndStatus(UserEntity r, UserEntity s, Status status);

    List<RequestEntity> findAllBySentRequestOwner(UserEntity loggedInUser);
    List<RequestEntity> findAllByReceivedRequestOwner(UserEntity loggedInUser);

    List<RequestEntity> findAllByReceivedRequestOwnerAndStatusIsNot(UserEntity loggedInUser, Status status);

    List<RequestEntity> findAllBySentRequestOwnerAndStatusIsNot(UserEntity loggedInUser, Status status);

}
