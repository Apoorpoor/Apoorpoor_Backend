package com.example.apoorpoor_backend.model.enumType;

public enum ExpType {
    FILL_LEDGER(100L);

    private final Long amount;

    ExpType(Long amount) {
        this.amount = amount;
    }

    public Long getAmount() {
        return amount;
    }

}
