package com.example.apoorpoor_backend.model.enumType.item;

public enum BottomsType {
    BOTTOMS_1(1L, "노란 바지", 50L, 1L),
    BOTTOMS_2(2L, "파란 바지", 100L, 2L);

    private final Long bottomsNum;

    private final String bottomsName;

    private final Long itemPrice;

    private final Long levelLimit;

    BottomsType(Long bottomsNum, String bottomsName, Long itemPrice, Long levelLimit) {
        this.bottomsNum = bottomsNum;
        this.bottomsName = bottomsName;
        this.itemPrice = itemPrice;
        this.levelLimit = levelLimit;
    }
}
