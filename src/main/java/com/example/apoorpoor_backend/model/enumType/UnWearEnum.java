package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum UnWearEnum {
    UN_WEAR_TOPS("tops"),
    UN_WEAR_BOTTOMS("bottoms"),
    UN_WEAR_SHOES("shoes"),
    UN_WEAR_ACCESSORIES("accessories");

    private final String unWearPart;

    UnWearEnum(String unWearPart) {
        this.unWearPart = unWearPart;
    }
}
