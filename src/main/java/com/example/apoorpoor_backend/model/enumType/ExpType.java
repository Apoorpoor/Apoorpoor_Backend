package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum ExpType {
    FILL_LEDGER(10L, "가계부 작성 완료!"),
    GET_BADGE(20L, "뱃지 획득!"),
    BEST_SAVER(10L, "저축 완료!"),
    LEVEL_UP(100L, "레벨 업!");

    private final Long amount;

    private final String description;

    ExpType(Long amount, String description) {
        this.amount = amount;
        this.description = description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

}
