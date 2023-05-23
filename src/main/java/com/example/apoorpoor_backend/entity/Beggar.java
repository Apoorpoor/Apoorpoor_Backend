package com.example.apoorpoor_backend.entity;

import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TB_BEGGAR")
public class Beggar extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beggar_id", unique = true, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String nickname;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long point;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long level;

    @Column
    private String mention;

}
