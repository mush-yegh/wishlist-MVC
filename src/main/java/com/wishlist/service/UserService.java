package com.wishlist.service;

import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public List<UserDto> findAllActiveUsers() {


        List<UserEntity> userEntities = userRepository.findAllByActive(1);
        /*
        List<User> result = users.stream()
            .filter(x -> x.getUsername().equalsIgnoreCase(username))
            .collect(Collectors.toList());
        return result;
        */
        return UserDto.mapEntityListToDto(userEntities);
    }

    public UserDto findUserById(Long id){
        UserEntity userEntity = userRepository.findOneById(id);
        return UserDto.mapEntityToDto(userEntity);
    }
}
