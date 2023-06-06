package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity(name = "USERS")
@Getter
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = true)
    private Long kakaoId;

    @Column
    private Long age;

    @Column
    private String gender;

    @CreationTimestamp
    private Timestamp createDate;

    @Column
    private Long sender;

    @Builder
    public User(String username, String password, UserRoleEnum role, Long kakaoId, Long sender) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
        this.sender = sender;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateAge(Long age) {
        this.age = age;
    }

    public void updateGender(String gender) {
        this.gender = gender;
    }
}
