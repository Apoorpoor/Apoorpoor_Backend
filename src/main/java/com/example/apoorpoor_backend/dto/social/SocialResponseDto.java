package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialResponseDto {
    private double percent;
    private Long expenditure;
    private Long income;
    private Double expenditure_avg;
    private Double income_avg;

    @QueryProjection
    @Builder
    public SocialResponseDto(double percent, Long expenditure, Long income, Double expenditure_avg, Double income_avg) {
        this.percent = percent;
        this.expenditure = expenditure;
        this.income = income;
        this.expenditure_avg = expenditure_avg;
        this.income_avg = income_avg;
    }
}
