package com.example.apoorpoor_backend.dto.user;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;
}