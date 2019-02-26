package com.wishlist.job;

import com.wishlist.persistance.entity.UserEntity;
import com.wishlist.security.details.UserDetailsServiceImpl;
import com.wishlist.service.FriendService;
import com.wishlist.service.UserService;
import com.wishlist.service.dto.FriendDto;
import com.wishlist.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.wishlist.service.dto.SocketResponseDto;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.util.List;

import static com.wishlist.util.StaticMethContainer.findLoggedInUser;

@Component
public class BdNotifier {
    @Autowired
    private UserService userService;
    @Autowired
    private FriendService friendService;

    @Autowired
    private SimpMessagingTemplate template;


    // (*) means match any
    // */X means "every X"
    // ? ("no specific value"
    //second, minute, hour, dayOfMonth, month, day(s) of week
    @Scheduled(cron = "0 42 * * * *", zone = "Asia/Dubai")
    public void checkBd() {

       // UserEntity loggedInUser = (UserEntity) authProvider.getPrincipal();

        List<UserEntity> allUsers = userService.findAllUsers();
        allUsers.stream()
                .forEach(u -> {
                    List<FriendDto> userFriends = friendService.getFriends(u);
                    userFriends.stream()
                            .filter(f -> /*{
                                System.out.println("f.getBirthDate() = " + f.getBirthDate());
                                System.out.println("LocalDate.now() = " + LocalDate.now());
                                return  (
                                        //f.getBirthDate().equals(LocalDate.now());
                                        f.getBirthDate().getMonth().equals(LocalDate.now().getMonth())
                                        && f.getBirthDate().getDayOfMonth()
                                        )
                            }*/
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

        /*friends.stream()
                .filter(f -> f.getBirthDate().equals(LocalDate.now()))
                .forEach(f -> {
                    SocketResponseDto resp = SocketResponseDto.builder()
                            .notifType("bd")
                            .recipientId(findLoggedInUser(auth).getId())
                            .sender(
                                    UserDto.builder()
                                            .firstName(f.getFirstName())
                                            .lastName(f.getLastName())
                                            .id(f.getId())
                                            .build()
                            )
                            .build();
                    template.convertAndSend("/client/notification", ResponseEntity.ok(resp));
                });*/
        //UserDto sender;

        //get loggedInUser friends, check for their bd and send notification

        /*SocketResponseDto resp = SocketResponseDto.builder()
                .notifType("bd")
                .recipientId(101L)
                .sender(null)
                .build();
        System.out.println("job is running");*/

        //template.convertAndSend("/client/notification", ResponseEntity.ok(resp));
    }
}
