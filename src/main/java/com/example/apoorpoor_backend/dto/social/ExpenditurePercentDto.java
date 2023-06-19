package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenditurePercentDto {

    private Long age_abb;
    private String date;
    private Long exp_sum;
    private String gender;
    private Long user_id;
    private Long my_rank;

    @QueryProjection
    @Builder
    public ExpenditurePercentDto(Long age_abb, String date, Long exp_sum, String gender, Long user_id, Long my_rank) {
        this.age_abb = age_abb;
        this.date = date;
        this.exp_sum = exp_sum;
        this.gender = gender;
        this.user_id = user_id;
        this.my_rank = my_rank;
    }
}
