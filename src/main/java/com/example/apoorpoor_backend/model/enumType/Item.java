package com.example.apoorpoor_backend.model.enumType;

import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum Item {
    ITEM_1(1L, "빨간 조끼", 50L, 1L, "tops"),
    ITEM_2(2L, "흰 셔츠", 100L, 2L, "tops"),
    ITEM_3(3L, "파란 반 바지", 50L, 1L, "bottoms"),
    ITEM_4(4L, "신사 바지", 100L, 2L, "bottoms"),
    ITEM_5(5L, "짚신", 50L, 1L, "shoes"),
    ITEM_6(6L, "고무신", 100L, 2L, "shoes"),
    ITEM_7(7L, "흰 셔츠", 50L, 1L, "accessories"),
    ITEM_8(8L, "흰 셔츠", 100L, 2L, "accessories");


    private final Long itemNum;

    private final String itemName;

    private final Long itemPrice;

    private final Long levelLimit;

    private final String itemType;

    Item(Long itemNum, String itemName, Long itemPrice, Long levelLimit, String itemType) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.levelLimit = levelLimit;
        this.itemType = itemType;
    }

    public static List<ItemResponseDto> getEnumItemList() {
        List<ItemResponseDto> itemList = new ArrayList<>();

        for (Item item : Item.values()) {
            ItemResponseDto dto = ItemResponseDto.builder()
                    .itemNum(item.getItemNum())
                    .itemName(item.getItemName())
                    .itemPrice(item.getItemPrice())
                    .levelLimit(item.getLevelLimit())
                    .itemType(item.getItemType())
                    .build();

            itemList.add(dto);
        }

        return itemList;
    }

    public static List<ItemResponseDto> getEnumItemListByType(String itemType) {
        List<ItemResponseDto> filteredItemList = new ArrayList<>();

        for (Item item : Item.values()) {
            if (item.getItemType().equals(itemType)) {
                ItemResponseDto dto = ItemResponseDto.builder()
                        .itemNum(item.getItemNum())
                        .itemName(item.getItemName())
                        .itemPrice(item.getItemPrice())
                        .levelLimit(item.getLevelLimit())
                        .itemType(item.getItemType())
                        .build();

                filteredItemList.add(dto);
            }
        }

        return filteredItemList;
    }
}
