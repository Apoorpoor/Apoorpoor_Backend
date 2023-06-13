package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpenditureTotalDto {
    private String date;
    private Long expSum;
    private Long beggarId;

    @QueryProjection
    @Builder
    public ExpenditureTotalDto(String date, Long expSum, Long beggarId) {
        this.date = date;
        this.expSum = expSum;
        this.beggarId = beggarId;
    }
}
