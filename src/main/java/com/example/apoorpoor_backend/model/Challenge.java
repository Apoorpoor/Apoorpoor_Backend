package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.ChallengeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    @Column
    private String username;

    @Column
    private ChallengeType challengeType;

    @Column
    private String title;

    @Column
    private Long challengeAmount;

    @Column
    @ColumnDefault("0")
    private Long weekExpenditure;

    @Column
    private Boolean successStatus;


    public void updateWeekExpenditure(Long weekExpenditure) {
        this.weekExpenditure += weekExpenditure;
    }

    public void updateSuccessStatus(Boolean successStatus) {
        this.successStatus = successStatus;
    }

}
