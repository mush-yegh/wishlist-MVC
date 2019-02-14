package com.wishlist.service;

import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.persistance.repository.RequestRepository;
import com.wishlist.service.dto.RequestDto;
import com.wishlist.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.wishlist.service.dto.RequestDto.mapDtoToEntity;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public RequestDto saveRequest(RequestDto requestDto){
        RequestEntity requestEntity = mapDtoToEntity(requestDto);
        RequestEntity entity = requestRepository.save(requestEntity);
        return RequestDto.mapEntityToDto(entity);
    }



}
