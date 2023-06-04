package com.example.apoorpoor_backend.dto.shop;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
@Getter
@Builder
public class ItemResponseDto {

    private Long itemNum;

    private String itemName;

    private Long itemPrice;

    private Long levelLimit;

    private String itemType;

//    public ItemResponseDto(Long itemNum, String itemName, Long itemPrice, Long levelLimit, String itemType) {
//        this.itemNum = itemNum;
//        this.itemName = itemName;
//        this.itemPrice = itemPrice;
//        this.levelLimit = levelLimit;
//        this.itemType = itemType;
//    }
}
