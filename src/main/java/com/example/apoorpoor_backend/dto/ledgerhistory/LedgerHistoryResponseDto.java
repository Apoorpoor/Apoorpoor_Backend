package com.example.apoorpoor_backend.dto.ledgerhistory;

import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
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
    @Builder
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

    public static LedgerHistoryResponseDto of(LedgerHistory ledgerHistory){
        return LedgerHistoryResponseDto.builder()
                .id(ledgerHistory.getId())
                .title(ledgerHistory.getTitle())
                .accountType(ledgerHistory.getAccountType())
                .incomeType(ledgerHistory.getIncomeType())
                .expenditureType(ledgerHistory.getExpenditureType())
                .paymentMethod(ledgerHistory.getPaymentMethod())
                .income(ledgerHistory.getIncome())
                .expenditure(ledgerHistory.getExpenditure())
                .date(ledgerHistory.getDate().toString())
                .build();
    }

    public LedgerHistoryResponseDto(LedgerHistory ledgerHistory){
        this.id = ledgerHistory.getId();
        this.title = ledgerHistory.getTitle();
        this.accountType = ledgerHistory.getAccountType();
        this.incomeType = ledgerHistory.getIncomeType();
        this.expenditureType = ledgerHistory.getExpenditureType();
        this.paymentMethod = ledgerHistory.getPaymentMethod();
        this.income = ledgerHistory.getIncome();
        this.expenditure = ledgerHistory.getExpenditure();
        this.date = ledgerHistory.getDate().toString();
    }

}
