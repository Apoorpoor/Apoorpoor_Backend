package com.example.apoorpoor_backend.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class AccountTotalListResponseDto {
    private List<AccountTotalResponseDto> accountTotalResponseDtoList;
    private Long expenditure_sum;
    private Long income_sum;

    public AccountTotalListResponseDto(List<AccountTotalResponseDto> accountTotalResponseDtoList, Long expenditure_sum, Long income_sum) {
        this.accountTotalResponseDtoList = accountTotalResponseDtoList;
        this.expenditure_sum = expenditure_sum;
        this.income_sum = income_sum;
    }
}
