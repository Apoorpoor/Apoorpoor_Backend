package com.example.apoorpoor_backend.dto.ledgerhistory;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
public class LedgerHistoryResponseDto {
    private Long id;
    private String title;
    private AccountType accountType;
    private IncomeType incomeType;
    private ExpenditureType expenditureType;
    private PaymentMethod paymentMethod;
    private Long income;
    private Long expenditure;
    private String date;


    @QueryProjection
    public LedgerHistoryResponseDto(Long id, String title, AccountType accountType, IncomeType incomeType,
                                     ExpenditureType expenditureType, PaymentMethod paymentMethod, Long income, Long expenditure, String date) {
        this.id = id;
        this.title = title;
        this.accountType = accountType;
        this.incomeType = incomeType;
        this.expenditureType = expenditureType;
        this.paymentMethod = paymentMethod;
        this.income = income;
        this.expenditure = expenditure;
        this.date = date;
    }
}
