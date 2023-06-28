package com.example.apoorpoor_backend.dto.user;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class GenderRequestDto {
    @NotEmpty(message = "gender")
    private String gender;
}
