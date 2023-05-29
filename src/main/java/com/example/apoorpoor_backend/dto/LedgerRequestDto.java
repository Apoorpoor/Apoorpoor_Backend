package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LedgerRequestDto {

    private String ledgerTitle;
    private String incomeType;
    private String expenditureType;
    private Long income;
    private Long expenditure;
    private LocalDateTime dateTime;
    private AccountType accountType;
}
