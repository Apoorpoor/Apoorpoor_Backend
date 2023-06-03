package com.example.apoorpoor_backend.model.enumType.item;

public enum BottomsType {
    BOTTOMS_1(1L, "노란 바지", 50L),
    BOTTOMS_2(2L, "파란 바지", 100L);

    private final Long bottomsNum;

    private final String bottomsName;

    private final Long itemPrice;

    BottomsType(Long bottomsNum, String bottomsName, Long itemPrice) {
        this.bottomsNum = bottomsNum;
        this.bottomsName = bottomsName;
        this.itemPrice = itemPrice;
    }
}
