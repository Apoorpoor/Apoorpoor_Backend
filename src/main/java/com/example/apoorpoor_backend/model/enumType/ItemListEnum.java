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

    //tops
    ITEM_TOPS_0(0L, "촉촉한 종이 박스", 20L, 2L, "tops"),
    ITEM_TOPS_1(1L, "꼬질꼬질 홀터넥", 20L, 3L, "tops"),
    ITEM_TOPS_2(2L, "꼬질꼬질 난닝구", 20L, 3L, "tops"),
    ITEM_TOPS_3(3L, "구찌..아니 아구찜", 20L, 4L, "tops"),
    ITEM_TOPS_4(4L, "늘어난 반팔", 20L, 4L, "tops"),
    ITEM_TOPS_5(5L, "의류수거함 카라티", 20L, 4L, "tops"),
    ITEM_TOPS_6(6L, "거지의 첫 셔츠", 40L, 5L, "tops"),
    ITEM_TOPS_7(7L, "거지의 소개팅 셔츠", 40L, 5L, "tops"),
    ITEM_TOPS_8(8L, "시장에서 산 맨투맨", 40L, 5L, "tops"),
    ITEM_TOPS_9(9L, "스마일 맨투맨", 20L, 5L, "tops"),
    ITEM_TOPS_10(10L, "아보카도 맨투맨", 20L, 5L, "tops"),
    ITEM_TOPS_11(11L, "노랑 맨투맨", 20L, 5L, "tops"),
    ITEM_TOPS_12(12L, "반팔 웨이터 유니폼", 60L, 6L, "tops"),
    ITEM_TOPS_13(13L, "반팔 웨이터 유니폼", 60L, 6L, "tops"),
    ITEM_TOPS_14(14L, "블레이저", 60L, 6L, "tops"),
    ITEM_TOPS_15(15L, "블레이저", 60L, 6L, "tops"),

    //bottoms
    ITEM_BOTTOMS_0(16L, "길에서 주운 청 반바지", 20L, 4L, "bottoms"),
    ITEM_BOTTOMS_1(17L, "길에서 주운 면 반바지", 20L, 4L, "bottoms"),
    ITEM_BOTTOMS_2(18L, "물새는 수영복 바지", 20L, 4L, "bottoms"),
    ITEM_BOTTOMS_3(19L, "검은 면바지", 40L, 5L, "bottoms"),
    ITEM_BOTTOMS_4(20L, "남색 면바지", 40L, 5L, "bottoms"),
    ITEM_BOTTOMS_5(21L, "연청바지", 40L, 5L, "bottoms"),
    ITEM_BOTTOMS_6(22L, "크림진", 40L, 5L, "bottoms"),
    ITEM_BOTTOMS_7(23L, "조거팬츠", 40L, 5L, "bottoms"),
    ITEM_BOTTOMS_8(24L, "트레이닝 팬츠", 40L, 6L, "bottoms"),
    ITEM_BOTTOMS_9(25L, "슬렉스", 60L, 6L, "bottoms"),
    ITEM_BOTTOMS_10(26L, "슬렉스", 60L, 6L, "bottoms"),

    //shoes
    ITEM_SHOES_0(27L, "고무신", 20L, 3L, "shoes"),
    ITEM_SHOES_1(28L, "구멍난 운동화", 20L, 4L, "shoes"),
    ITEM_SHOES_2(29L, "너덜너덜 슬리퍼", 20L, 4L, "shoes"),
    ITEM_SHOES_3(30L, "닥터마팅", 40L, 5L, "shoes"),
    ITEM_SHOES_4(31L, "닥터마팅", 40L, 5L, "shoes"),
    ITEM_SHOES_5(32L, "닥터마팅", 40L, 5L, "shoes"),
    ITEM_SHOES_6(33L, "방스 단화", 40L, 5L, "shoes"),
    ITEM_SHOES_7(34L, "방스 단화", 40L, 5L, "shoes"),
    ITEM_SHOES_8(35L, "컹버스", 40L, 5L, "shoes"),
    ITEM_SHOES_9(36L, "에어포숙", 40L, 5L, "shoes"),
    ITEM_SHOES_10(37L, "된장포숙", 40L, 5L, "shoes"),
    ITEM_SHOES_11(38L, "조단", 70L, 6L, "shoes"),
    ITEM_SHOES_12(39L, "독일군 스니커즈", 6L, 6L, "shoes"),
    ITEM_SHOES_13(40L, "구두", 60L, 6L, "shoes"),
    ITEM_SHOES_14(41L, "구두", 60L, 6L, "shoes"),
    ITEM_SHOES_15(42L, "구두", 60L, 6L, "shoes"),

    //accessories
    ITEM_ACC_0(43L, "면도기", 60L, 6L, "shaves"),
    ITEM_ACC_1(44L, "로락스 시계", 60L, 6L, "watches"),
    ITEM_ACC_2(45L, "사과 워치", 60L, 6L, "watches"),
    ITEM_ACC_3(46L, "수영모", 60L, 6L, "hats"),

    //customs
    ITEM_CUSTOM_0(47L, "의사 거지", 20L, 7L, "customs"),
    ITEM_CUSTOM_1(48L, "우주인 거지", 20L, 8L, "customs"),
    ITEM_CUSTOM_2(49L, "산타 거지", 20L, 9L, "customs"),
    ITEM_CUSTOM_3(50L, "석유왕", 20L, 10L, "customs");


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