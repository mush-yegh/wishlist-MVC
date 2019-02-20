package com.wishlist.service;

import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.service.dto.RequestDto;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.Status;
import com.wishlist.persistance.entity.RequestEntity;
import com.wishlist.persistance.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static com.wishlist.service.dto.RequestDto.mapDtoToEntity;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    UserRepository userRepository;

    public RequestDto saveRequest(RequestDto requestDto) {
        RequestEntity requestEntity = mapDtoToEntity(requestDto);
        requestEntity.setSentRequestOwner(userRepository.findOneById(Long.valueOf(requestDto.getSenderId())));
        requestEntity.setReceivedRequestOwner(userRepository.findOneById(Long.valueOf(requestDto.getRecipientId())));

        RequestEntity entity = requestRepository.save(requestEntity);
        return RequestDto.mapEntityToDto(entity);
    }


    public Optional<RequestDto> rejectRequest(Long recipId, Long senderId) {
        /*Optional<RequestEntity> reqToReject = requestRepository.findOneByRecipientIdAndAndSenderId(recipId, senderId);
        if (reqToReject.isPresent()){
            reqToReject.get().setStatus(Status.REJECTED);
            reqToReject.get().setResponseDate(LocalDate.now());
            requestRepository.save(reqToReject.get());
            return Optional.of(RequestDto.mapEntityToDto(reqToReject.get()));
        }*/
        return Optional.empty();
    }

    public Optional<RequestDto> acceptRequest(Long recipId, Long senderId) {
        /*Optional<RequestEntity> reqToAccept = requestRepository.findOneByRecipientIdAndAndSenderId(recipId, senderId);
        if (reqToAccept.isPresent()){
            reqToAccept.get().setStatus(Status.ACCEPTED);
            reqToAccept.get().setResponseDate(LocalDate.now());
            requestRepository.save(reqToAccept.get());
            return Optional.of(RequestDto.mapEntityToDto(reqToAccept.get()));
        }*/
        return Optional.empty();
    }

    public Optional<List<RequestEntity>> findUserSentRequests(Long loggedInUserId) {
        UserEntity userEntity = userRepository.findOneById(loggedInUserId);

        List<RequestEntity> sentRequests = userEntity.getSentRequests();
        if (sentRequests.size() > 0)
            return Optional.of(sentRequests);

        return Optional.empty();
    }

    public Optional<List<RequestDto>> findUserReceivedRequests(Long loggedInUserId) {
        /*Optional<List<RequestEntity>> requestEntities =
                requestRepository.findAllByRecipientIdAndStatusOrderByRequestDateAsc(loggedInUserId, Status.PENDING);
        return requestEntities.map(RequestDto::mapEntitiesToDtos);*/
        return Optional.empty();
    }
}
