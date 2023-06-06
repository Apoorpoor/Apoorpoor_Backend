package com.example.apoorpoor_backend.dto.account;

import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class TotalSumResponseDto {
    private ExpenditureType expenditureType;
    private Long total_sum;

    @QueryProjection
    public TotalSumResponseDto(ExpenditureType expenditureType, Long total_sum) {
        this.expenditureType = expenditureType;
        this.total_sum = total_sum;
    }
}
