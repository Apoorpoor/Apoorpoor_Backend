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
    private Long age_abb;
    private String gender;
    private Long expenditure_sum;
    private Long income_sum;

    @Builder
    public SocialResponseDto(double percent, Long expenditure, Long income, Double expenditure_avg,
                             Double income_avg, Long age_abb, String gender, Long expenditure_sum, Long income_sum) {
        this.percent = percent;
        this.expenditure = expenditure;
        this.income = income;
        this.expenditure_avg = expenditure_avg;
        this.income_avg = income_avg;
        this.age_abb = age_abb;
        this.gender = gender;
        this.expenditure_sum = expenditure_sum;
        this.income_sum = income_sum;
    }
}
