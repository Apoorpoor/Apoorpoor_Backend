package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "BALANCE")
@Getter
@NoArgsConstructor
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private Long incomeTotal;

    @Column
    private Long expenditureTotal;



//    public void addLedgerList(FinancialLedger financialLedger) {
//        this.financialLedgerList.add(financialLedger);
//    }
//
    public Balance(Long incomeTotal, Long expenditureTotal, Account account) {
        this.incomeTotal = incomeTotal;
        this.expenditureTotal = expenditureTotal;
        this.account = account;
    }

    public void update(Long incomeTotal, Long expenditureTotal) {
        this.incomeTotal = incomeTotal;
        this.expenditureTotal = expenditureTotal;
    }
}