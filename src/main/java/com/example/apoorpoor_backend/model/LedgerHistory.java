package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity(name = "LEDGER_HISTORY")
@Table
public class LedgerHistory extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ledgerhistory_id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private String title;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private IncomeType incomeType;

    @Enumerated(EnumType.STRING)
    private ExpenditureType expenditureType;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column
    private Long income;

    @Column
    private Long expenditure;

    @Column
    private LocalDate date;

    public LedgerHistory(Account account, String title, AccountType accountType, IncomeType incomeType, ExpenditureType expenditureType,
                         PaymentMethod paymentMethod, Long income, Long expenditure, LocalDate date){
        this.account = account;
        this.title = title;
        this.accountType = accountType;
        this.incomeType = incomeType;
        this.expenditureType = expenditureType;
        this.paymentMethod = paymentMethod;
        this.income = income;
        this.expenditure = expenditure;
        this.date = date;

    }

    public void update(LedgerHistoryRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.accountType = requestDto.getAccountType();
        this.incomeType = requestDto.getIncomeType();
        this.expenditureType = requestDto.getExpenditureType();
        this.paymentMethod = requestDto.getPaymentMethod();
        this.income = requestDto.getIncome();
        this.expenditure = requestDto.getExpenditure();
        this.date = LocalDate.parse(requestDto.getDatetime());
    }

}
