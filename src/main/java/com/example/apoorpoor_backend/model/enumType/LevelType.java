package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum LevelType {
    LV1(1L, 100L),
    LV2(2L, 300L),
    LV3(3L, 600L),
    LV4(4L, 1000L),
    LV5(5L, 1500L),
    LV6(6L, 2100L),
    LV7(7L, 2800L),
    LV8(8L, 3600L),
    LV9(9L, 4500L),
    LV10(10L, 5500L);

    private final Long level;
    private final Long nextExp;

    LevelType(Long level, Long nextExp) {
        this.level = level;
        this.nextExp = nextExp;
    }

    public static Long getNextExpByLevel(Long level) {
        for (LevelType levelType : LevelType.values()) {
            if (levelType.level.equals(level)) {
                return levelType.nextExp;
            }
        }
        return null;
    }

    
}
