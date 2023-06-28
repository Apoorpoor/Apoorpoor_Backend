package com.example.apoorpoor_backend.dto.account;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class IncomeSumResponseDto {

    private String month;
    private Long income_sum;

    @QueryProjection
    public IncomeSumResponseDto(String month, Long income_sum) {
        this.month = month;
        this.income_sum = income_sum;
    }
}
