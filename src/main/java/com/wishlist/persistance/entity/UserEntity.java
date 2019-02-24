package com.wishlist.persistance.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "mail", unique = true, nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String hashPassword;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @Column(name = "created")
    private LocalDate created;

    @OneToMany(mappedBy = "sentRequestOwner")
    @JsonBackReference
    private List<RequestEntity> sentRequests;

    @OneToMany(mappedBy = "receivedRequestOwner")
    @JsonBackReference
    private List<RequestEntity> receivedRequests;

    @OneToMany(mappedBy = "wishOwner")
    @JsonBackReference
    private List<WishEntity> wishEntities;

    /*@OneToMany(mappedBy = "friendOwner")
    @JsonBackReference
    private List<FriendEntity> friendOwner;*/

    @OneToMany(mappedBy = "friend")
    @JsonBackReference
    private List<FriendEntity> friends;
}

