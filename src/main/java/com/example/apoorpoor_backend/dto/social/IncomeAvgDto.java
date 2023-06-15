package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IncomeAvgDto {
    private Long age_abb;
    private String date;
    private Long inc_count;
    private Long inc_sum;
    private Double inc_avg;
    private String gender;

    @QueryProjection
    @Builder
    public IncomeAvgDto(Long age_abb, String date, Long inc_count, Long inc_sum, Double inc_avg, String gender) {
        this.age_abb = age_abb;
        this.date = date;
        this.inc_count = inc_count;
        this.inc_sum = inc_sum;
        this.inc_avg = inc_avg;
        this.gender = gender;
    }
}
