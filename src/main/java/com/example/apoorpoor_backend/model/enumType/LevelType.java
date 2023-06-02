package com.example.apoorpoor_backend.model.enumType;

public enum LevelType {
    LV1(1L, 500L),
    LV2(2L, 1000L),
    LV3(3L, 1600L);

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
        return null; // 예외 처리 필요 (존재하지 않는 레벨일 경우)
    }

    public Long getNextExp() {
        return nextExp;
    }

}
