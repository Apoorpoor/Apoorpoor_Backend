package com.example.apoorpoor_backend.dto.account;

import lombok.Builder;
import lombok.Getter;


import java.util.List;

@Getter
@Builder
public class AccountTotalListResponseDto {
    private List<AccountTotalResponseDto> accountTotalResponseDtoList;
    private Long expenditure_sum;
    private Long income_sum;

}
