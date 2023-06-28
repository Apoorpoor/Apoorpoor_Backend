package com.example.apoorpoor_backend.dto.account;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ExpenditureSumResponseDto {
    private String month;
    private Long expenditure_sum;

    @QueryProjection
    public ExpenditureSumResponseDto(String month, Long expenditure_sum) {
        this.month = month;
        this.expenditure_sum = expenditure_sum;
    }
}
