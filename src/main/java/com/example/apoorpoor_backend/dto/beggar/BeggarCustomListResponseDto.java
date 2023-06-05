package com.example.apoorpoor_backend.dto.beggar;

import com.example.apoorpoor_backend.model.Item;
import lombok.Getter;

import java.util.List;

@Getter
public class BeggarCustomListResponseDto {
    private List<Item> itemsCollectionList;

    public BeggarCustomListResponseDto(List<Item> itemsCollectionList) {
        this.itemsCollectionList = itemsCollectionList;
    }
}
