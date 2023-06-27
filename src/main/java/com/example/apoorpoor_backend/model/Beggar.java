package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity(name = "BEGGAR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private ItemListEnum top;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemListEnum bottom;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemListEnum shoes;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemListEnum acc;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemListEnum custom;

    @Column
    private String challengeTitle;

    @Column
    private long successCount;

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
        this.exp += plusPoint;
        this.point += plusPoint;
    }

    public void updatePoint(Long plusPoint){
        this.point += plusPoint;
    }

    public void updateCustomTops(ItemListEnum itemListEnum) {
        this.top = itemListEnum;
        this.custom = null;
    }

    public void updateCustomBottoms(ItemListEnum itemListEnum) {
        this.bottom = itemListEnum;
        this.custom = null;
    }

    public void updateCustomShoes(ItemListEnum itemListEnum) {
        this.shoes = itemListEnum;
        this.custom = null;
    }

    public void updateCustomAccessories(ItemListEnum itemListEnum) {
        this.acc = itemListEnum;
        this.custom = null;
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

    public void setUser(User user) {
        this.user = user;
        user.setBeggar(this);
    }

    public void updateChallenge(String challengeTitle) {
        this.challengeTitle = challengeTitle;
    }

    public void resetChallenge() {
        this.challengeTitle = null;
    }

    public void updateSuccessCount() {
        this.successCount += 1;
    }

    public void updateResetSuccessCount() {
        this.successCount = 0;
    }
}