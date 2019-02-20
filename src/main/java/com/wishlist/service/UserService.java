package com.wishlist.service;

import com.wishlist.service.dto.UserDto;
import com.wishlist.persistance.entity.State;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.security.details.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import com.wishlist.persistance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> findAllActiveUsers(Authentication auth) {
        //get logged in user
        UserDetailsImpl details = (UserDetailsImpl)auth.getPrincipal();
        UserEntity loggedInUser = details.getUserEntity();

        List<UserEntity> userEntities = userRepository.findAllByState(State.ACTIVE);
        //List<UserEntity> friends = userRepository;

        //TO DO remove friends from userEntities list
        //userEntities.removeIf(u -> friends.contains(u));

        //TO DO remove users with pending requests

        userEntities = userEntities.stream()
                .filter(u -> !u.getMail().equalsIgnoreCase(loggedInUser.getMail())
                        //&& u -> ...
                )
                .collect(Collectors.toList());

        return UserDto.mapEntityListToDto(userEntities);
    }

    public UserDto findUserById(Long id){
        UserEntity userEntity = userRepository.findOneById(id);
        return UserDto.mapEntityToDto(userEntity);
    }
}
