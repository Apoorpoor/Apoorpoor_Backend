package com.example.apoorpoor_backend.model.enumType.item;

public enum TopsType {
    TOPS_1(1L, "빨간 조끼", 50L),
    TOPS_2(2L, "파란 조끼", 100L);

    private final Long topsNum;

    private final String topsName;

    private final Long itemPrice;

    TopsType(Long topsNum, String topsName, Long itemPrice) {
        this.topsNum = topsNum;
        this.topsName = topsName;
        this.itemPrice = itemPrice;
    }
}
