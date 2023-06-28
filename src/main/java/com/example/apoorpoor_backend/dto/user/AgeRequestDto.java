package com.example.apoorpoor_backend.dto.user;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AgeRequestDto {
    @NotNull(message = "age")
    private Long age;
}
