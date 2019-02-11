package com.wishlist.service;

import com.wishlist.persistance.entity.Role;
import com.wishlist.persistance.entity.State;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SignUpService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Optional<UserDto> signUp(UserDto userDto) {
        String hashPassword = passwordEncoder.encode(userDto.getPassword());
        UserEntity userEntity = UserDto.mapDtoToEntity(userDto);
        //or
        UserEntity userEntity1 = UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDate(LocalDate.parse(userDto.getBirthDate()))
                .mail(userDto.getMail())
                .hashPassword(passwordEncoder.encode(userDto.getPassword()))
                .role(Role.USER)
                .state(State.ACTIVE)
                .created(LocalDate.now()).build();

        userEntity.setHashPassword(hashPassword);

        UserEntity savedUserEntity = (UserEntity) userRepository.save(userEntity);

        /*if (savedUserEntity.isPresent())
        return (UserDto) UserDto.mapEntityToDto(userEntity);
        else return Optional.empty();*/
        return null;
    }
}
