package com.wishlist.service.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.wishlist.persistance.entity.WishEntity;
import com.wishlist.persistance.entity.FriendEntity;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private List<WishEntity> wishList;

    public static FriendDto mapEntityToDto(FriendEntity friendEntity){
        return FriendDto.builder()
                .id(friendEntity.getId())
                .firstName(friendEntity.getFriend().getFirstName())
                .lastName(friendEntity.getFriend().getLastName())
                .birthDate(friendEntity.getFriend().getBirthDate())
                .wishList(friendEntity.getFriend().getWishEntities())
                .build();
    }

    public static List<FriendDto> mapEntitiesToDtos(List<FriendEntity> friendEntities) {
        return friendEntities.stream()
                .map(FriendDto::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
