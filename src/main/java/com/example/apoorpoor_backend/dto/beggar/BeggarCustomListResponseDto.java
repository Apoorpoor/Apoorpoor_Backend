package com.example.apoorpoor_backend.dto.beggar;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class BeggarCustomListResponseDto {
    private List<ItemDto> itemsCollectionList;

    public BeggarCustomListResponseDto(List<ItemDto> itemsCollectionList) {
        this.itemsCollectionList = itemsCollectionList;
    }
}
