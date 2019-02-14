package com.wishlist.persistance.repository;

import com.wishlist.persistance.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findOneByMail(String mail);

    //List<UserEntity> findAllByActive(Integer val);
    List<UserEntity> findTop10AllByActive(Integer val);
    List<UserEntity> findAllByActive(Integer val);

    UserEntity findOneById(Long id);
}
