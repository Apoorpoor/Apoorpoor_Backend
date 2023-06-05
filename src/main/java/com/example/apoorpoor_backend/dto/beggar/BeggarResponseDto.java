package com.example.apoorpoor_backend.dto.beggar;

import com.example.apoorpoor_backend.model.Beggar;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BeggarResponseDto {
    private Long beggar_id;
    private Long user_id;
    private String nickname;
    private Long point;
    private Long level;
    private String description;

    @Builder
    private BeggarResponseDto(Long beggar_id, Long user_id, String nickname, Long point, Long level, String description){
        this.beggar_id = beggar_id;
        this.user_id = user_id;
        this.nickname = nickname;
        this.point = point;
        this.level = level;
        this.description = description;
    }

    public static BeggarResponseDto of(Beggar beggar){
        return BeggarResponseDto.builder()
                .beggar_id(beggar.getId())
                .user_id(beggar.getUser().getId())
                .nickname(beggar.getNickname())
                .point(beggar.getPoint())
                .level(beggar.getLevel())
                .description(beggar.getDescription())
                .build();
    }
}
