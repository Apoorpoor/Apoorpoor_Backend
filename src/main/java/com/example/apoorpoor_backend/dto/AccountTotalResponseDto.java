package com.example.apoorpoor_backend.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class AccountTotalResponseDto {

    private String day;
    private Long expenditure_sum;
    private Long income_sum;

    @QueryProjection
    public AccountTotalResponseDto(String day, Long expenditure_sum, Long income_sum) {
        this.day = day;
        this.expenditure_sum = expenditure_sum;
        this.income_sum = income_sum;
    }
}
