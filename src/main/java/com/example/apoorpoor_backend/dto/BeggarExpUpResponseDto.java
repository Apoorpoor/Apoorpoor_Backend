package com.example.apoorpoor_backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeggarExpUpResponseDto {
    private String nickname;

    private Long level;

    private Long exp;

    private Long point;
}
