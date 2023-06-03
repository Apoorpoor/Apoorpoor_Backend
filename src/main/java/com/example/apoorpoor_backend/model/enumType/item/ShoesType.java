package com.example.apoorpoor_backend.model.enumType.item;

public enum ShoesType {
    SHOES_1(1L, "주황 운동화", 50L),
    SHOES_2(2L, "초록 슬리퍼", 100L);

    private final Long shoesNum;

    private final String shoesName;

    private final Long itemPrice;

    ShoesType(Long shoesNum, String shoesName, Long itemPrice) {
        this.shoesNum = shoesNum;
        this.shoesName = shoesName;
        this.itemPrice = itemPrice;
    }
}
