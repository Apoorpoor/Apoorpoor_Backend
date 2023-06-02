package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Getter
@Entity(name = "BEGGAR")
@NoArgsConstructor
@Table
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

    @ColumnDefault("1")
    @Column(nullable = false)
    private Long level;

    @Column
    private String description;

    @ColumnDefault("0")
    @Column
    private Long exp;

    public Beggar(BeggarRequestDto requestDto, User user){
        this.nickname = requestDto.getNickname();
        this.user = user;
        this.point = 0L;
        this.level = 1L;
        this.exp = 0L;
    }

    public void update(BeggarRequestDto beggarRequestDto) {
        this.nickname = beggarRequestDto.getNickname();
    }

    public void updateExp(BeggarExpUpResponseDto responseDto) {
        this.nickname = responseDto.getNickname();
        this.exp = responseDto.getExp();
        this.level = responseDto.getLevel();
    }
}