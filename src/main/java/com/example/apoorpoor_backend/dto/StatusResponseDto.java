package com.example.apoorpoor_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class StatusResponseDto {
    private String meassage;
    public StatusResponseDto(String meassage){
        this.meassage = meassage;
    }
}