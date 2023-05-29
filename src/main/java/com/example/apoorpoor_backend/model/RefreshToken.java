package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "REFRESH_TOKEN")
@Table
public class RefreshToken extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refreshtoken_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String refreshToken;
}
