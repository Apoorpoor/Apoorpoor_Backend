package com.example.apoorpoor_backend.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private String meassage;
    private Long point;
    public StatusResponseDto(String meassage){
        this.meassage = meassage;
    }

    public StatusResponseDto(String meassage, Long point) {
        this.meassage = meassage;
        this.point = point;
    }
}