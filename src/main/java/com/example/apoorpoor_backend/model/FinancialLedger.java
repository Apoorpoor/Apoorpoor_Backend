package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity(name = "FINANCIAL_LEDGER")
@Getter
@NoArgsConstructor
public class FinancialLedger extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String ledgerTitle;

    @Column
    private String incomeType;

    @Column
    private String expenditureType;

    @Column
    private  Long income;

    @Column
    private Long expenditure;

    @Column
    private LocalDateTime dateTime;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    public FinancialLedger(LedgerResponseDto ledgerResponseDto) {
        this.username = ledgerResponseDto.getUsername();
        this.ledgerTitle = ledgerResponseDto.getLedgerTitle();
        this.incomeType = ledgerResponseDto.getIncomeType();
        this.expenditureType = ledgerResponseDto.getExpenditureType();
        this.income = ledgerResponseDto.getIncome();
        this.expenditure = ledgerResponseDto.getExpenditure();
        this.dateTime = ledgerResponseDto.getDateTime();
        this.accountType = ledgerResponseDto.getAccountType();
    }

    public void update(LedgerResponseDto ledgerResponseDto){
        this.ledgerTitle = ledgerResponseDto.getLedgerTitle();
        this.incomeType = ledgerResponseDto.getIncomeType();
        this.expenditureType = ledgerResponseDto.getExpenditureType();
        this.income = ledgerResponseDto.getIncome();
        this.expenditure = ledgerResponseDto.getExpenditure();
        this.dateTime = ledgerResponseDto.getDateTime();
        this.accountType = ledgerResponseDto.getAccountType();
    }
}