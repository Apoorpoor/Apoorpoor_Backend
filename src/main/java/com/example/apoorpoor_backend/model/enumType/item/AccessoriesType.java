package com.example.apoorpoor_backend.model.enumType.item;

public enum AccessoriesType {
    ACCESSORIES_1(1L, "선글라스", 50L),
    ACCESSORIES_2(2L, "우산", 100L);

    private final Long accessoryNum;

    private final String accessoryName;

    private final Long itemPrice;

    AccessoriesType(Long accessoryNum, String accessoryName, Long itemPrice) {
        this.accessoryNum = accessoryNum;
        this.accessoryName = accessoryName;
        this.itemPrice = itemPrice;
    }
}
