package com.example.apoorpoor_backend.dto.beggar;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeggarResponseDto {
    private Long beggar_id;
    private Long user_id;
    private String nickname;
    private Long point;
    private Long level;
    private String description;
}
