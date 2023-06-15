package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenditureAvgDto {
    private Long age_abb;
    private String date;
    private Long exp_count;
    private Long exp_sum;
    private Double exp_avg;
    private String gender;

    @QueryProjection
    @Builder
    public ExpenditureAvgDto(Long age_abb, String date, Long exp_count, Long exp_sum, Double exp_avg, String gender) {
        this.age_abb = age_abb;
        this.date = date;
        this.exp_count = exp_count;
        this.exp_sum = exp_sum;
        this.exp_avg = exp_avg;
        this.gender = gender;
    }
}
