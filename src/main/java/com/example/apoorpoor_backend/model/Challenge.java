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
public class Challenge {
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
    private Boolean isMonday;

    @Column
    private Boolean isTuesday;

    @Column
    private Boolean isWednesday;

    @Column
    private Boolean isThursday;

    @Column
    private Boolean isFriday;

    @Column
    private Boolean isSaturday;

    @Column
    private Boolean isSunday;

    @Column
    private int successCount;

    public void updateWeekExpenditure(Long weekExpenditure) {
        this.weekExpenditure += weekExpenditure;
    }

    public void updateIsMonday(Boolean isMonday) {
        this.isMonday = isMonday;
        if(isMonday) this.successCount += 1;
    }

    public void updateIsTuesday(Boolean isTuesday) {
        this.isTuesday = isTuesday;
        if(isTuesday) this.successCount += 1;
    }

    public void updateIsWednesday(Boolean isWednesday) {
        this.isWednesday = isWednesday;
        if(isWednesday) this.successCount += 1;
    }

    public void updateIsThursday(Boolean isThursday) {
        this.isThursday = isThursday;
        if(isThursday) this.successCount += 1;
    }

    public void updateIsFriday(Boolean isFriday) {
        this.isFriday = isFriday;
        if(isFriday) this.successCount += 1;
    }

    public void updateIsSaturday(Boolean isSaturday) {
        this.isSaturday = isSaturday;
        if(isSaturday) this.successCount += 1;
    }

    public void updateIsSunday(Boolean isSunday) {
        this.isSunday = isSunday;
        if(isSunday) this.successCount += 1;
    }
}
