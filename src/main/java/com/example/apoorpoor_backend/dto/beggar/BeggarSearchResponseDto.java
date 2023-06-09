package com.example.apoorpoor_backend.dto.beggar;

import com.example.apoorpoor_backend.model.Badge;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BeggarSearchResponseDto {
    private Long beggarId;

    private Long userId;

    private String nickname;

    private Long point;

    private Long exp;

    private List<Badge> badgeList;

    private Long level;

    private String description;

    private String gender;

    private Long age;

    private String topImage;

    private String bottomImage;

    private String shoesImage;

    private String accImage;

    private String customImage;


}
