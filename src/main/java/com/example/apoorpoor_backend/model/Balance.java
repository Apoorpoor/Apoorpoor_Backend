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

    @Column
    private Long incomeTotal;

    @Column
    private Long expenditureTotal;

    @Column
    private String ledgerTitle;

    @OneToMany
    @JoinColumn(name = "balance_id")
    private List<FinancialLedger> financialLedgerList = new ArrayList<>();


    public void addLedgerList(FinancialLedger financialLedger) {
        this.financialLedgerList.add(financialLedger);
    }

    public Balance(Long incomeTotal, Long expenditureTotal, String ledgerTitle) {
        this.incomeTotal = incomeTotal;
        this.expenditureTotal = expenditureTotal;
        this.ledgerTitle = ledgerTitle;
    }

    public void update(Long incomeTotal, Long expenditureTotal) {
        this.incomeTotal = incomeTotal;
        this.expenditureTotal = expenditureTotal;
    }
}