package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.enumType.ExpType;
import lombok.Getter;

@Getter
public class BeggarExpUpRequestDto {
    private String nickname;

    private Long level;

    private Long exp;

    private Long point;

    private ExpType expType;
}
