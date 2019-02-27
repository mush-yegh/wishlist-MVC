package com.wishlist.persistence.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend")
public class FriendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_one_id")
    private Long userOneId;

    @ManyToOne
    @JoinColumn(name = "user_two_id")
    @JsonManagedReference
    private UserEntity friend;

    @Column(name = "is_deleted", nullable = false)
    private Integer isDeleted;
}
