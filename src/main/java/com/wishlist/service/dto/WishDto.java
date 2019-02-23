package com.wishlist.service.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.wishlist.persistance.entity.WishEntity;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDto {
    private Long id;
    //private UserDto owner;
    private String title;
    private String link;
    private String description;
    private double price;

    public static WishDto mapEntityToDto(WishEntity wishEntity){
        return WishDto.builder()
                .id(wishEntity.getId())
                .title(wishEntity.getTitle())
                .link(wishEntity.getLink())
                .description(wishEntity.getDescription())
                .price(wishEntity.getPrice())
                .build();
    }
    public static List<WishDto> mapEntityListToDtoList(List<WishEntity> wishEntityList){
        return wishEntityList.stream()
                .map(WishDto::mapEntityToDto)
                .collect(Collectors.toList());
    }

    public static WishEntity mapDtoToEntity(WishDto wishDto) {
        return WishEntity.builder()
                //.wishOwner(wishDto.getOwner())
                .title(wishDto.getTitle())
                .link(wishDto.getLink())
                .description(wishDto.getDescription())
                .build();

    }
}
