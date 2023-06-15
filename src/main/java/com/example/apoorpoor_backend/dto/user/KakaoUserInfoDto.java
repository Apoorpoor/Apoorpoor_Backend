package com.example.apoorpoor_backend.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class KakaoUserInfoDto {
    private Long id;
    private String nickname;

}