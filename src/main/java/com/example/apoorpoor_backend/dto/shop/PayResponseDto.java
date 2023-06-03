package com.example.apoorpoor_backend.dto.shop;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PayResponseDto {
    private ItemResponseDto itemResponseDto;

    private Long point;
}
