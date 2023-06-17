package com.example.apoorpoor_backend.dto.beggar;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class BeggarRequestDto {
    @NotEmpty(message = "nickname")
    private String nickname;
}
