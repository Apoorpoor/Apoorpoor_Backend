package com.example.apoorpoor_backend.dto.social;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
}
