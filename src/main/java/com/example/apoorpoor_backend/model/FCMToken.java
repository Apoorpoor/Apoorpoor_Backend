package com.example.apoorpoor_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "FCM_TOKEN")
@NoArgsConstructor
public class FCMToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String fcmToken;

    @NotBlank
    private String username;

    public FCMToken(String fcmToken, String username) {
        this.fcmToken = fcmToken;
        this.username = username;
    }


    public FCMToken updateToken(String fcmToken) {
        this.fcmToken = fcmToken;
        return this;
    }
}
