package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@Entity(name = "LEDGER_HISTORY")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public void update(LedgerHistoryResponseDto responseDto) {
        this.title = responseDto.getTitle();
        this.accountType = responseDto.getAccountType();
        this.incomeType = responseDto.getIncomeType();
        this.expenditureType = responseDto.getExpenditureType();
        this.paymentMethod = responseDto.getPaymentMethod();
        this.income = responseDto.getIncome();
        this.expenditure = responseDto.getExpenditure();
        this.date = LocalDate.parse(responseDto.getDate());
    }

}