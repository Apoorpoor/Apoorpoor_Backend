package com.example.apoorpoor_backend.dto.beggar;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BeggarInfoDto {
    private Long beggar_id;
    private String nickname;
}
