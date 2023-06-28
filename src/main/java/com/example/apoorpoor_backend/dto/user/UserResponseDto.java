package com.example.apoorpoor_backend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponseDto {
    private Long id;
    private String username;
    private Long age;
    private String gender;
}
