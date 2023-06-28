package com.example.apoorpoor_backend.dto.beggar;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BeggarRequestDto {
    @NotEmpty(message = "nickname")
    private String nickname;
}
