package com.example.apoorpoor_backend.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private String status;
    private String field;

    public ErrorResponse(String status, String field) {
        this.status = status;
        this.field = field;
    }

}