package com.example.apoorpoor_backend.dto.account;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class AccountRequestDto {
    @NotEmpty(message = "title")
    private String title;
}
