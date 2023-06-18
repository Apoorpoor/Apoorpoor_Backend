package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.ChallengeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    @Column
    private ChallengeType challengeType;

    @Column
    private String title;

    @Column
    private Long challengeAmount;

    @Column
    private Long weekExpenditure;

    @Column
    private boolean isSuccess;

    @Column
    private boolean isMonday;

    @Column
    private boolean isTuesday;

    @Column
    private boolean isWednesday;

    @Column
    private boolean isThursday;

    @Column
    private boolean isFriday;

    @Column
    private boolean isSaturday;

    @Column
    private boolean isSunday;


}
