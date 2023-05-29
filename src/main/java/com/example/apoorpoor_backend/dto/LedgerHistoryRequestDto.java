package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import lombok.Getter;

@Getter
public class LedgerHistoryRequestDto {
    private Long accountId;
    private String title;
    private AccountType accountType;
    private IncomeType incomeType;
    private ExpenditureType expenditureType;
    private PaymentMethod paymentMethod;
    private Long income;
    private Long expenditure;
    private String datetime;
}
