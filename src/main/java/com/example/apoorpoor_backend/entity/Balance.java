package com.example.apoorpoor_backend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "TB_BALANCE")
@Getter
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long id;

    @Column
    private Long incomeTotal;

    @Column
    private Long expenditureTotal;

    @OneToMany
    @JoinColumn(name = "balance_id")
    private List<FinancialLedger> financialLedgerList = new ArrayList<>();


    public void addLedgerList(FinancialLedger financialLedger) {
        this.financialLedgerList.add(financialLedger);
    }


}
