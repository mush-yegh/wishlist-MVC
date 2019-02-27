package com.wishlist.service;

import com.wishlist.service.dto.UserDto;
import com.wishlist.persistence.entity.Role;
import com.wishlist.persistence.entity.State;
import org.springframework.stereotype.Service;
import com.wishlist.persistence.entity.UserEntity;
import com.wishlist.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.time.LocalDate;

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
