package com.example.apoorpoor_backend.dto.shop;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ItemListResponseDto {
    private List<ItemResponseDto> itemList;

    public ItemListResponseDto(List<ItemResponseDto> itemList) {
        this.itemList = itemList;
    }
}
