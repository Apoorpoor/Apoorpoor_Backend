package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "POINT")
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Point extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    private String pointDescription;

    private Long earnedPoint;

    private Long usedPoints;

    @ManyToOne
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

}
