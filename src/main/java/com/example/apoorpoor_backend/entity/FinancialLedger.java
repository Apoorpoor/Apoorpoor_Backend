package com.example.apoorpoor_backend.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "TB_FINANCIAL_LEDGER")
@Getter
public class FinancialLedger extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

}
