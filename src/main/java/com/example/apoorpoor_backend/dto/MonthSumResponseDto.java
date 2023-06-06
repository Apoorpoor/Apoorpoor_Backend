package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class MonthSumResponseDto {
    private String month;
    private ExpenditureType expenditureType;
    private Long month_sum;

    @QueryProjection
    public MonthSumResponseDto(String month, ExpenditureType expenditureType, Long month_sum) {
        this.month = month;
        this.expenditureType = expenditureType;
        this.month_sum = month_sum;
    }

    @QueryProjection
    public MonthSumResponseDto(String month, Long month_sum) {
        this.month = month;
        this.month_sum = month_sum;
    }
}
