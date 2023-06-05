package com.example.apoorpoor_backend.dto.beggar;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemDto {
    private Long itemNum;

    private String itemName;

    private Long levelLimit;

    private String itemType;

    public ItemDto(Long itemNum, String itemName, Long levelLimit, String itemType) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.levelLimit = levelLimit;
        this.itemType = itemType;
    }
}
