package com.wishlist.service.dto;

import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.persistance.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Long id;

    private String userFromId;
    private String userToId;
    private String status;
    private LocalDate requestDate;
    private LocalDate responseDate;

    public static RequestEntity mapDtoToEntity(RequestDto requestDto){
        return RequestEntity.builder()
                .requesterId(Long.parseLong(requestDto.getUserFromId()))
                .receiverId(Long.parseLong(requestDto.getUserToId()))
                .status(Status.PENDING)
                .requestDate(LocalDate.now())
                .build();
    }

    public static RequestDto mapEntityToDto(RequestEntity requestEntity){
        return RequestDto.builder()
                .userFromId(String.valueOf(requestEntity.getRequesterId()))
                .userToId(String.valueOf(requestEntity.getReceiverId()))
                .status(String.valueOf(requestEntity.getStatus()))
                .requestDate(requestEntity.getRequestDate())
                .responseDate(requestEntity.getResponseDate())
                .build();
    }

}
