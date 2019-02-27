package com.wishlist.persistence.repository;

import com.wishlist.persistence.entity.State;
import com.wishlist.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findOneByMail(String mail);

    List<UserEntity> findAllByState(State state);

    UserEntity findOneById(Long id);

    //List<UserEntity> findAllBy

}
