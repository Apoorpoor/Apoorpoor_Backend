package com.example.apoorpoor_backend.dto.social;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class IncomeTotalDto {
    private String date;
    private Long incSum;
    private Long beggarId;

    @QueryProjection
    @Builder
    public IncomeTotalDto(String date, Long incSum, Long beggarId) {
        this.date = date;
        this.incSum = incSum;
        this.beggarId = beggarId;
    }
}
