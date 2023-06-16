package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity(name = "USERS")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String username;

    @Column(nullable = false)
    private String kakaoname;

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

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Beggar beggar;

    @Builder
    public User(String username, String password, UserRoleEnum role, Long kakaoId, String kakaoname) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.kakaoId = kakaoId;
        this.kakaoname = kakaoname;
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

    public void setBeggar(Beggar beggar) {
        this.beggar = beggar;
        beggar.setUser(this);
    }
}
