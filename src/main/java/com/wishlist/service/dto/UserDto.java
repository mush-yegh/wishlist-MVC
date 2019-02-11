package com.wishlist.service.dto;

import com.wishlist.persistance.entity.Role;
import com.wishlist.persistance.entity.State;
import com.wishlist.persistance.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String firstName;
    private String lastName;
    private String birthDate;
    private String mail;
    private String password;
    private Role role;
    private String state;

    public static UserEntity mapDtoToEntity(UserDto userDto) {
        return UserEntity.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .mail(userDto.getMail())
                //password
                .birthDate(LocalDate.parse(userDto.getBirthDate()))
                .active(1)
                .created(LocalDate.now())
                .role(Role.USER)
                .state(State.ACTIVE)
                .build();
    }

    public static UserDto mapEntityToDto(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .mail(userEntity.getMail())
                .birthDate((userEntity.getBirthDate()).toString())
                .build();
    }

    public static List<UserDto> mapEntityListToDto(List<UserEntity> userEntities) {

        return userEntities.stream()
                .map(UserDto::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
