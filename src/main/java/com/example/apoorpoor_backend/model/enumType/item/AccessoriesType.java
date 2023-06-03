package com.example.apoorpoor_backend.model.enumType.item;

public enum AccessoriesType {
    ACCESSORIES_1(1L, "선글라스", 50L, 1L),
    ACCESSORIES_2(2L, "우산", 100L, 2L);

    private final Long accessoryNum;

    private final String accessoryName;

    private final Long itemPrice;

    private final Long levelLimit;

    AccessoriesType(Long accessoryNum, String accessoryName, Long itemPrice, Long levelLimit) {
        this.accessoryNum = accessoryNum;
        this.accessoryName = accessoryName;
        this.itemPrice = itemPrice;
        this.levelLimit = levelLimit;
    }
}
