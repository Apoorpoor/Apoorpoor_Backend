package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity(name = "POINT")
@Table
@Builder
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
