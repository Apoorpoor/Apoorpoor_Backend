package com.example.apoorpoor_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private String meassage;
    public StatusResponseDto(String meassage){
        this.meassage = meassage;
    }
}