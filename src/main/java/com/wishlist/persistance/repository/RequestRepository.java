package com.wishlist.persistance.repository;

import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
//public interface RequestRepository{

    Optional<RequestEntity> findOneByRecipientIdAndAndSenderId(Long recipientId, Long senderId);
}
