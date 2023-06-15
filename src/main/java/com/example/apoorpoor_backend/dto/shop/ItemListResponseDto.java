package com.example.apoorpoor_backend.dto.shop;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ItemListResponseDto {
    private List<ItemResponseDto> itemList;

}
