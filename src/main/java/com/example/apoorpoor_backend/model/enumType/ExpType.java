package com.example.apoorpoor_backend.model.enumType;

public enum ExpType {
    FILL_LEDGER(10L),
    GET_BADGE(20L);

    private final Long amount;

    ExpType(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

}
