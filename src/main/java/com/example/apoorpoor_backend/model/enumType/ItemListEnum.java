package com.example.apoorpoor_backend.model.enumType;

import com.example.apoorpoor_backend.dto.shop.ItemResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static List<ItemResponseDto> getEnumItemList(Beggar beggar, List<Long> hasItemNumDtoList) {
        List<ItemResponseDto> itemList = new ArrayList<>();

        int itemListEnumCount = (int) Arrays.stream(ItemListEnum.values()).count();
        Long topsNum = beggar.getTops() == null ? null : beggar.getTops().getItemNum();
        Long bottomsNum = beggar.getBottoms() == null ? null : beggar.getBottoms().getItemNum();
        Long shoesNum = beggar.getShoes() == null ? null : beggar.getShoes().getItemNum();
        Long accessoriesNum = beggar.getAccessories() == null ? null : beggar.getAccessories().getItemNum();

        String[] matches = new String[itemListEnumCount];

        for (Long aLong : hasItemNumDtoList) {
            matches[Math.toIntExact(aLong)] = "DONE";
        }

        int i = 0;
        for (ItemListEnum itemListEnum : ItemListEnum.values()) {
            Long itemNum = itemListEnum.getItemNum();
            String itemName = itemListEnum.getItemName();
            Long itemPrice = itemListEnum.getItemPrice();
            Long levelLimit = itemListEnum.getLevelLimit();
            String itemType = itemListEnum.getItemType();
            String itemState = matches[i];

            if (Objects.equals(topsNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(bottomsNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(shoesNum, itemNum)) itemState = "EQUIPPED";
            if (Objects.equals(accessoriesNum, itemNum)) itemState = "EQUIPPED";

            ItemResponseDto dto = ItemResponseDto.builder()
                    .itemNum(itemNum)
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .levelLimit(levelLimit)
                    .itemType(itemType)
                    .itemState(itemState)
                    .build();
            itemList.add(dto);

            i++;
        }
        return itemList;
    }


    public static List<ItemResponseDto> getEnumItemListByType(String itemType, Beggar beggar, List<Long> hasItemNumDtoList) {
        List<ItemResponseDto> filteredItemList = new ArrayList<>();

        int itemListEnumCount = (int) Arrays.stream(ItemListEnum.values()).count();
        Long topsNum = beggar.getTops() == null ? null : beggar.getTops().getItemNum();
        Long bottomsNum = beggar.getBottoms() == null ? null : beggar.getBottoms().getItemNum();
        Long shoesNum = beggar.getShoes() == null ? null : beggar.getShoes().getItemNum();
        Long accessoriesNum = beggar.getAccessories() == null ? null : beggar.getAccessories().getItemNum();

        String[] matches = new String[itemListEnumCount];

        for (Long aLong : hasItemNumDtoList) {
            matches[Math.toIntExact(aLong)] = "DONE";
        }

        int i = 0;

        for (ItemListEnum itemListEnum : ItemListEnum.values()) {
            if (itemListEnum.getItemType().equals(itemType)) {

                Long itemNum = itemListEnum.getItemNum();
                String itemName = itemListEnum.getItemName();
                Long itemPrice = itemListEnum.getItemPrice();
                Long levelLimit = itemListEnum.getLevelLimit();
                String itemState = matches[i];


                if (Objects.equals(topsNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(bottomsNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(shoesNum, itemNum)) itemState = "EQUIPPED";
                if (Objects.equals(accessoriesNum, itemNum)) itemState = "EQUIPPED";


                ItemResponseDto dto = ItemResponseDto.builder()
                        .itemNum(itemNum)
                        .itemName(itemName)
                        .itemPrice(itemPrice)
                        .levelLimit(levelLimit)
                        .itemType(itemType)
                        .itemState(itemState)
                        .build();
                filteredItemList.add(dto);

            }
            i++;
        }
        return filteredItemList;
    }
}