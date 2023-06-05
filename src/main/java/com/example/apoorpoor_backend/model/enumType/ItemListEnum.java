package com.example.apoorpoor_backend.model.enumType;

import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum ItemListEnum {
    ITEM_0(0L, "빨간 조끼", 50L, 1L, "tops"),
    ITEM_1(1L, "흰 셔츠", 100L, 2L, "tops"),
    ITEM_2(2L, "파란 반 바지", 50L, 1L, "bottoms"),
    ITEM_3(3L, "신사 바지", 100L, 2L, "bottoms"),
    ITEM_4(4L, "짚신", 50L, 1L, "shoes"),
    ITEM_5(5L, "고무신", 100L, 2L, "shoes"),
    ITEM_6(6L, "금 팔찌", 50L, 1L, "accessories"),
    ITEM_7(7L, "금 목걸이", 100L, 2L, "accessories");


    private final Long itemNum;

    private final String itemName;

    private final Long itemPrice;

    private final Long levelLimit;

    private final String itemType;

    ItemListEnum(Long itemNum, String itemName, Long itemPrice, Long levelLimit, String itemType) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.levelLimit = levelLimit;
        this.itemType = itemType;
    }

    public static List<ItemResponseDto> getEnumItemList(Long beggarLevel) {
        List<ItemResponseDto> itemList = new ArrayList<>();

        for (ItemListEnum itemListEnum : ItemListEnum.values()) {
            if(itemListEnum.getLevelLimit() <= beggarLevel) {
                ItemResponseDto dto = ItemResponseDto.builder()
                        .itemNum(itemListEnum.getItemNum())
                        .itemName(itemListEnum.getItemName())
                        .itemPrice(itemListEnum.getItemPrice())
                        .levelLimit(itemListEnum.getLevelLimit())
                        .itemType(itemListEnum.getItemType())
                        .build();

                itemList.add(dto);
            }
        }

        return itemList;
    }

    public static List<ItemResponseDto> getEnumItemListByType(String itemType, Long beggarLevel) {
        List<ItemResponseDto> filteredItemList = new ArrayList<>();

        for (ItemListEnum itemListEnum : ItemListEnum.values()) {
            if (itemListEnum.getItemType().equals(itemType)) {
                if(itemListEnum.getLevelLimit() <= beggarLevel) {
                    ItemResponseDto dto = ItemResponseDto.builder()
                            .itemNum(itemListEnum.getItemNum())
                            .itemName(itemListEnum.getItemName())
                            .itemPrice(itemListEnum.getItemPrice())
                            .levelLimit(itemListEnum.getLevelLimit())
                            .itemType(itemListEnum.getItemType())
                            .build();

                    filteredItemList.add(dto);
                }
            }
        }

        return filteredItemList;
    }
}
