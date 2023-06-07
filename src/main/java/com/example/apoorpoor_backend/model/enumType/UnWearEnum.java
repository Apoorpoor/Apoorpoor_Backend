package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum UnWearEnum {
    top("top"),
    bottom("bottom"),
    shoes("shoes"),
    acc("acc"),
    custom("custom");

    private final String unWearPart;

    UnWearEnum(String unWearPart) {
        this.unWearPart = unWearPart;
    }
}