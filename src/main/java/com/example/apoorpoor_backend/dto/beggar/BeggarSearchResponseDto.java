package com.example.apoorpoor_backend.dto.beggar;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BeggarSearchResponseDto {
    private Long beggarId;

    private Long userId;

    private String nickname;

    private Long point;

    private Long level;

    private String description;

    private String gender;

    private Long age;

    public BeggarSearchResponseDto(Long beggarId, Long userId, String nickname, Long point, Long level, String description, String gender, Long age) {
        this.beggarId = beggarId;
        this.userId = userId;
        this.nickname = nickname;
        this.point = point;
        this.level = level;
        this.description = description;
        this.gender = gender;
        this.age = age;
    }
}
