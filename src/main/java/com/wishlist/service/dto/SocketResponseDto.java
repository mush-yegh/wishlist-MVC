package com.wishlist.service.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketResponseDto {
    //private String msg;
    private Long recipientId;
    private UserDto sender;
}
