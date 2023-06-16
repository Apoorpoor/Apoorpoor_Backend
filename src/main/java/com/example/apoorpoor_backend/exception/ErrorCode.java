package com.example.apoorpoor_backend.exception;

import lombok.Getter;

public enum ErrorCode {

    NOT_NULL("400")
    ,NOT_EMPTY("400")
    ;

    @Getter
    private String status;

    ErrorCode(String status) {
        this.status = status;
    }
}