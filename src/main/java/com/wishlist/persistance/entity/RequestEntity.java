package com.wishlist.persistance.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "request")
public class RequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    /*@Column(name = "sender_id", nullable = false)
    private Long senderId;*/
    @ManyToOne/*(fetch = FetchType.EAGER)*/
    @JoinColumn(name = "sender_id")
    @JsonManagedReference
    private UserEntity sentRequestOwner;

    /*@Column(name = "recipient_id", nullable = false)
    private Long recipientId;*/
    @ManyToOne/*(fetch = FetchType.LAZY)*/
    @JoinColumn(name = "recipient_id")
    @JsonManagedReference
    private UserEntity receivedRequestOwner;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "response_date")
    private LocalDate responseDate;


}
