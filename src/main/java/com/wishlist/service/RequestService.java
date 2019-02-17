package com.wishlist.service;

import com.wishlist.service.dto.RequestDto;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.Status;
import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.persistance.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static com.wishlist.service.dto.RequestDto.mapDtoToEntity;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public RequestDto saveRequest(RequestDto requestDto) {
        RequestEntity requestEntity = mapDtoToEntity(requestDto);
        RequestEntity entity = requestRepository.save(requestEntity);
        return RequestDto.mapEntityToDto(entity);
    }


    public Optional<RequestDto> rejectRequest(Long recipId, Long senderId) {
        Optional<RequestEntity> reqToReject = requestRepository.findOneByRecipientIdAndAndSenderId(recipId, senderId);
        if (reqToReject.isPresent()){
            reqToReject.get().setStatus(Status.REJECTED);
            reqToReject.get().setResponseDate(LocalDate.now());
            requestRepository.save(reqToReject.get());
            return Optional.of(RequestDto.mapEntityToDto(reqToReject.get()));
        }
        return Optional.empty();
    }

    public Optional<RequestDto> acceptRequest(Long recipId, Long senderId) {
        Optional<RequestEntity> reqToAccept = requestRepository.findOneByRecipientIdAndAndSenderId(recipId, senderId);
        if (reqToAccept.isPresent()){
            reqToAccept.get().setStatus(Status.ACCEPTED);
            reqToAccept.get().setResponseDate(LocalDate.now());
            requestRepository.save(reqToAccept.get());
            return Optional.of(RequestDto.mapEntityToDto(reqToAccept.get()));
        }
        return Optional.empty();
    }
}
