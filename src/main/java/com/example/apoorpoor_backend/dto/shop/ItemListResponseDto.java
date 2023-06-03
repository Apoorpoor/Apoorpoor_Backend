package com.example.apoorpoor_backend.dto.shop;

import java.util.List;

public class ItemListResponseDto {
    private List<ItemResponseDto> itemList;

    public ItemListResponseDto(List<ItemResponseDto> itemList) {
        this.itemList = itemList;
    }
}
