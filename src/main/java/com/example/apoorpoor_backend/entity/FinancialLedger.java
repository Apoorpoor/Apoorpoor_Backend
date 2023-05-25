package com.example.apoorpoor_backend.entity;

import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "TB_FINANCIAL_LEDGER")
@Getter
@NoArgsConstructor
public class FinancialLedger extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memberId;

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
        this.memberId = ledgerResponseDto.getMemberId();
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
