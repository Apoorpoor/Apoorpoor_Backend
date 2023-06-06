package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


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

    @ColumnDefault("1")
    @Column(nullable = false)
    private Long level;

    @Column
    private String description;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Long point;

    @ColumnDefault("0")
    @Column
    private Long exp;

    @OneToMany(mappedBy = "beggar")
    private List<GetBadge> getBadgeList = new ArrayList<>();

    @Column
    private ItemListEnum top;

    @Column
    private ItemListEnum bottom;

    @Column
    private ItemListEnum shoes;

    @Column
    private ItemListEnum acc;

    @Column
    private ItemListEnum custom;

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
        this.point = responseDto.getPoint();
    }

    public void updatePointAndExp(Long plusPoint){
        this.point += plusPoint;
        this.exp += plusPoint;
    }

    public void updateCustomTops(ItemListEnum itemListEnum) {
        this.top = itemListEnum;
    }

    public void updateCustomBottoms(ItemListEnum itemListEnum) {
        this.bottom = itemListEnum;
    }

    public void updateCustomShoes(ItemListEnum itemListEnum) {
        this.shoes = itemListEnum;
    }

    public void updateCustomAccessories(ItemListEnum itemListEnum) {
        this.acc = itemListEnum;
    }


    public void updateCustoms(ItemListEnum itemListEnum) {
        this.custom = itemListEnum;
        this.top = null;
        this.bottom = null;
        this.shoes = null;
        this.acc = null;
    }

    public void updateLevel(Long level) {
        this.level = level;
    }
    /*
        /*
    필요한 파라미터 : ExpType expType; BadgeType badgeType;
     Beggar beggar = beggarCheck(username);
        String nickname = beggar.getNickname();
        ExpType expType = beggarExpUpRequestDto.getExpType();

        Long exp = beggar.getExp() + expType.getAmount();
        Long point = beggar.getPoint() + expType.getAmount();
        Long level = beggar.getLevel();

        String pointDescription = expType.getDescription();

        if(expType.equals(ExpType.GET_BADGE)) {
            pointDescription = ExpType.GET_BADGE.getDescription();
            saveBadge(beggarExpUpRequestDto.getBadgeType(), beggar);
        }

        if(expType.equals(ExpType.BEST_SAVER)) {
            pointDescription = ExpType.BEST_SAVER.getDescription();
        }

        if(expType.equals(ExpType.LEVEL_UP)) {
            pointDescription = ExpType.LEVEL_UP.getDescription();
        }

        if (LevelType.getNextExpByLevel(level) <= exp) {
            level ++;
        }

        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
                .nickname(nickname)
                .exp(exp)
                .level(level)
                .point(point)
                .build();

        Point recordPoint = new Point(pointDescription, point, null, beggar);
        pointRepository.save(recordPoint);

        beggar.updateExp(beggarExpUpResponseDto);

        return new ResponseEntity<>(beggarExpUpResponseDto, HttpStatus.OK);
     */
}