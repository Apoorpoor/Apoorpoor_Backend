package com.example.apoorpoor_backend.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private String message;
    private Long point;
    public StatusResponseDto(String message){
        this.message = message;
    }

    public StatusResponseDto(String message, Long point) {
        this.message = message;
        this.point = point;
    }
}