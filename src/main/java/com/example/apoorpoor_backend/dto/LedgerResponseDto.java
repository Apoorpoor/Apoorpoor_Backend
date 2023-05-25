package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.entity.AccountType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LedgerResponseDto {

    private String memberId;
    private String ledgerTitle;
    private String incomeType;
    private String expenditureType;
    private Long income;
    private Long expenditure;
    private LocalDateTime dateTime;
    private AccountType accountType;

}
