package com.example.apoorpoor_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity(name ="TB_MEMBER")
@Getter
public class Member extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    @ColumnDefault("0")
    private Long point;

    @Column(name = "provider")
    private String provider;

}
