package com.example.apoorpoor_backend.dto.beggar;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeggarSearchResponseDto {
    private Long beggarId;

    private Long userId;

    private String nickname;

    private Long point;

    private Long level;

    private String description;

    private String gender;

    private Long age;

    private String topImage;

    private String bottomImage;

    private String shoesImage;

    private String accImage;

    private String customImage;

    //입고있는것 추가

//    public BeggarSearchResponseDto(Long beggarId, Long userId, String nickname, Long point, Long level, String description, String gender, Long age) {
//        this.beggarId = beggarId;
//        this.userId = userId;
//        this.nickname = nickname;
//        this.point = point;
//        this.level = level;
//        this.description = description;
//        this.gender = gender;
//        this.age = age;
//    }
}
