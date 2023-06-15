package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "SOCIAL")
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Social {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_id", unique = true, nullable = false)
    private Long id;

    @Column
    private String date;

    @Column
    private String gender;

    @Column
    private Long age_abb;

    @Column
    private Long exp_count;

    @Column
    private Long exp_sum;

    @Column
    private Double exp_avg;

    @Column
    private Long inc_count;

    @Column
    private Long inc_sum;

    @Column
    private Double inc_avg;

    public void updateExp(String date, Long expCount, Long expSum, Double expAvg) {
        this.date = date;
        this.exp_count = expCount;
        this.exp_sum = expSum;
        this.exp_avg = expAvg;
    }

    public void updateInc(String date, Long incCount, Long incSum, Double incAvg) {
        this.date = date;
        this.inc_count = incCount;
        this.inc_sum = incSum;
        this.inc_avg = incAvg;
    }
}
