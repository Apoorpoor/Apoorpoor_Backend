package com.example.apoorpoor_backend.dto.account;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class RecentSumResponseDto {
    private List<ExpenditureSumResponseDto> expenditureSum;
    private List<IncomeSumResponseDto> incomeSum;
}
