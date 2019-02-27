package com.wishlist.job;

import com.wishlist.service.UserService;
import com.wishlist.service.dto.UserDto;
import com.wishlist.service.FriendService;
import com.wishlist.service.dto.FriendDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.wishlist.persistence.entity.UserEntity;
import com.wishlist.service.dto.SocketResponseDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.time.LocalDate;


@Component
public class BdNotifier {
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

    @Autowired
    private SimpMessagingTemplate template;

    //second, minute, hour, dayOfMonth, month, day(s) of week
    @Scheduled(cron = "0 0 10 * * *", zone = "Asia/Dubai") //10:00 AM every day.
    //@Scheduled(fixedRate = 5*60*1000) //for demo
    public void checkBd() {

        List<UserEntity> allUsers = userService.findAllUsers();
        allUsers.stream()
                .forEach(u -> {
                    List<FriendDto> userFriends = friendService.getFriends(u);
                    userFriends.stream()
                            .filter(f ->
                                    (f.getBirthDate().getMonth()+""+f.getBirthDate().getDayOfMonth())
                                            .equals(LocalDate.now().getMonth()+""+LocalDate.now().getDayOfMonth())
                            )
                            .forEach(f -> {
                                SocketResponseDto resp = SocketResponseDto.builder()
                                        .notifType("bd")
                                        .recipientId(u.getId())
                                        .sender(
                                                UserDto.builder()
                                                        .firstName(f.getFirstName())
                                                        .lastName(f.getLastName())
                                                        .id(f.getId())
                                                        .build()
                                        )
                                        .build();
                                template.convertAndSend("/client/notification", ResponseEntity.ok(resp));
                            });
                });
    }
}
