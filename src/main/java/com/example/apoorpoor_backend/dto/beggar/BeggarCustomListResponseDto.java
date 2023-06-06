package com.example.apoorpoor_backend.dto.beggar;

import com.example.apoorpoor_backend.model.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class BeggarCustomListResponseDto {
    private List<ItemDto> itemsCollectionList;

    public BeggarCustomListResponseDto(List<ItemDto> itemsCollectionList) {
        this.itemsCollectionList = itemsCollectionList;
    }
}
