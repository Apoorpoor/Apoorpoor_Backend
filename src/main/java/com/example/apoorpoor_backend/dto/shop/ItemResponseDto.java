package com.example.apoorpoor_backend.dto.shop;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponseDto {

    private Long itemNum;

    private String itemName;

    private Long itemPrice;

    private Long levelLimit;

    private String itemType;

    private String itemState;

    private String itemImage;

}

