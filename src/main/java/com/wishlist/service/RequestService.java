package com.wishlist.service;

import com.wishlist.service.dto.RequestDto;
import com.wishlist.persistance.entity.Status;
import org.springframework.stereotype.Service;
import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.persistance.entity.RequestEntity;
import org.springframework.security.core.Authentication;
import com.wishlist.persistance.repository.UserRepository;
import com.wishlist.persistance.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRepository userRepository;

    public RequestDto saveRequest(Authentication auth, Long recipId) {
        Long loggedInUserId = findLoggedInUser(auth).getId();
//        RequestEntity requestEntity = mapDtoToEntity(requestDto);
        RequestEntity requestEntity = RequestEntity.builder()
                .sentRequestOwner(userRepository.findOneById(loggedInUserId))
                .receivedRequestOwner(userRepository.findOneById(recipId))
                .status(Status.PENDING)
                .requestDate(LocalDate.now())
                .build();

        RequestEntity entity = requestRepository.save(requestEntity);
        return RequestDto.mapEntityToDto(entity);
    }


    public Optional<RequestDto> rejectRequest(Authentication auth, Long senderId) {
        Long recipId = findLoggedInUser(auth).getId();
        //check if request is present
        Optional<RequestEntity> reqToReject =
                requestRepository.findOneBySentRequestOwnerAndReceivedRequestOwnerAndStatus(
                        userRepository.findOneById(senderId),
                        userRepository.findOneById(recipId),
                        Status.PENDING);

        if (reqToReject.isPresent()) {
            reqToReject.get().setStatus(Status.REJECTED);
            reqToReject.get().setResponseDate(LocalDate.now());
            requestRepository.save(reqToReject.get());
            return Optional.of(RequestDto.mapEntityToDto(reqToReject.get()));
        }
        return Optional.empty();
    }

    public Optional<RequestDto> acceptRequest(Authentication auth, Long senderId) {
        Long recipId = findLoggedInUser(auth).getId();
        //check if request is present
        Optional<RequestEntity> reqToAccept =
                requestRepository.findOneBySentRequestOwnerAndReceivedRequestOwnerAndStatus(
                        userRepository.findOneById(senderId),
                        userRepository.findOneById(recipId),
                        Status.PENDING);
        if (reqToAccept.isPresent()){
            reqToAccept.get().setStatus(Status.ACCEPTED);
            reqToAccept.get().setResponseDate(LocalDate.now());
            requestRepository.save(reqToAccept.get());
            return Optional.of(RequestDto.mapEntityToDto(reqToAccept.get()));
        }
        return Optional.empty();
    }

    public List<RequestDto> findUserSentRequests(Authentication auth) {
        UserEntity loggedInUser = findLoggedInUser(auth);

        List<RequestEntity> sentRequests = requestRepository.findAllBySentRequestOwner(loggedInUser);
            return RequestDto.mapEntitiesToDtos(sentRequests);
    }

    public List<RequestDto> findUserReceivedRequests(Authentication auth) {
        UserEntity loggedInUser = findLoggedInUser(auth);

        List<RequestEntity> receivedRequests = requestRepository.findAllByReceivedRequestOwner(loggedInUser);
        return RequestDto.mapEntitiesToDtos(receivedRequests);
    }


}
