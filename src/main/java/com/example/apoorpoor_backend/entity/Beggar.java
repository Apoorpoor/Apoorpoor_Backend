package com.example.apoorpoor_backend.entity;

import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
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

    public Beggar(BeggarRequestDto requestDto, User user){
        this.nickname = requestDto.getNickname();
        this.user = user;
        this.point = 0L;
        this.level = 0L;
    }

    public void update(BeggarRequestDto beggarRequestDto) {
        this.nickname = beggarRequestDto.getNickname();
    }
}