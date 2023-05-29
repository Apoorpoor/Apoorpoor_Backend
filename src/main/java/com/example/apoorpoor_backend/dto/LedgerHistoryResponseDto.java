package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LedgerHistoryResponseDto {
    private Long accountId;
    private String title;
    private AccountType accountType;
    private IncomeType incomeType;
    private ExpenditureType expenditureType;
    private PaymentMethod paymentMethod;
    private Long income;
    private Long expenditure;
    private LocalDate date;

    @Builder
    private LedgerHistoryResponseDto(Long accountId, String title, AccountType accountType, IncomeType incomeType,
                                     ExpenditureType expenditureType, PaymentMethod paymentMethod, Long income, Long expenditure, LocalDate date) {
        this.accountId = accountId;
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
                .accountId(ledgerHistory.getAccount().getId())
                .title(ledgerHistory.getTitle())
                .accountType(ledgerHistory.getAccountType())
                .incomeType(ledgerHistory.getIncomeType())
                .expenditureType(ledgerHistory.getExpenditureType())
                .paymentMethod(ledgerHistory.getPaymentMethod())
                .income(ledgerHistory.getIncome())
                .expenditure(ledgerHistory.getExpenditure())
                .date(ledgerHistory.getDate())
                .build();
    }
}
