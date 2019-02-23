package com.wishlist.service.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.wishlist.persistance.entity.Status;
import com.wishlist.persistance.entity.RequestEntity;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long requestId;

    private UserDto sender;
    private UserDto recipient;

    private String status;
    private LocalDate requestDate;
    private LocalDate responseDate;

    /*public static RequestEntity mapDtoToEntity(RequestDto requestDto){
        return RequestEntity.builder()
                //.sentRequestOwner()
                //.senderId(Long.parseLong(requestDto.getSenderId()))
                //.recipientId(Long.parseLong(requestDto.getRecipientId()))
                .status(Status.PENDING)
                .requestDate(LocalDate.now())
                .build();
    }*/

    public static RequestDto mapEntityToDto(RequestEntity requestEntity){
        return RequestDto.builder()
                .requestId(requestEntity.getRequestId())
                .sender(UserDto.mapEntityToDto(requestEntity.getSentRequestOwner()))
                .recipient(UserDto.mapEntityToDto(requestEntity.getReceivedRequestOwner()))
                .status(String.valueOf(requestEntity.getStatus()))
                .requestDate(requestEntity.getRequestDate())
                .responseDate(requestEntity.getResponseDate())
                .build();
    }

    public static List<RequestDto> mapEntitiesToDtos(List<RequestEntity> requestEntities){
        return requestEntities.stream()
                .map(RequestDto::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
