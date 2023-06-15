package com.example.apoorpoor_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity(name = "BALANCE")
@Getter
@Builder
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "balance_id")
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column
    private Long incomeTotal;

    @Column
    private Long expenditureTotal;

    public void update(Long incomeTotal, Long expenditureTotal) {
        this.incomeTotal = incomeTotal;
        this.expenditureTotal = expenditureTotal;
    }
}