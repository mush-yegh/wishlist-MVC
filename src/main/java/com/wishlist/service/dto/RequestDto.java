package com.wishlist.service.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.wishlist.persistance.entity.Status;
import com.wishlist.persistance.entity.RequestEntity;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long requestId;

    private String senderId;
    private String recipientId;
    private String status;
    private LocalDate requestDate;
    private LocalDate responseDate;

    //private String msg;

    public static RequestEntity mapDtoToEntity(RequestDto requestDto){
        return RequestEntity.builder()
                .senderId(Long.parseLong(requestDto.getSenderId()))
                .recipientId(Long.parseLong(requestDto.getRecipientId()))
                .status(Status.PENDING)
                .requestDate(LocalDate.now())
                .build();
    }

    public static RequestDto mapEntityToDto(RequestEntity requestEntity){
        return RequestDto.builder()
                .requestId(requestEntity.getRequestId())
                .senderId(String.valueOf(requestEntity.getSenderId()))
                .recipientId(String.valueOf(requestEntity.getRecipientId()))
                .status(String.valueOf(requestEntity.getStatus()))
                .requestDate(requestEntity.getRequestDate())
                .responseDate(requestEntity.getResponseDate())
                .build();
    }

}
