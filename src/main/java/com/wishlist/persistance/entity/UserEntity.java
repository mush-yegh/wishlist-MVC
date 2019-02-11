package com.wishlist.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
//@ToString(exclude = "requests")
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

    @Column(name = "active")
    private Integer active;

    @Column(name = "created")
    private LocalDate created;

    /*@OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<WishEntity> wishes;*/
}
