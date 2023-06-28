package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "RANKING")
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id", unique = true, nullable = false)
    private Long id;

    @Column
    private Long rank_num;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    @Column
    private Long total;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column
    private String date;

    public void updateIncomeTotal(String date, Long incSum, Beggar beggar) {
        this.date = date;
        this.total = incSum;
        this.beggar = beggar;
    }

    public void updateExpenditureTotal(String date, Long expSum, Beggar beggar) {
        this.date = date;
        this.total = expSum;
        this.beggar = beggar;
    }
}
