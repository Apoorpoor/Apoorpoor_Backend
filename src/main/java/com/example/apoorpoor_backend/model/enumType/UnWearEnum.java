package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum UnWearEnum {
    UN_WEAR_TOP("top"),
    UN_WEAR_BOTTOM("bottom"),
    UN_WEAR_SHOES("shoes"),
    UN_WEAR_ACC("acc"),
    UN_WEAR_CUSTOM("custom");

    private final String unWearPart;

    UnWearEnum(String unWearPart) {
        this.unWearPart = unWearPart;
    }
}
