package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.ExpType;
import com.example.apoorpoor_backend.model.enumType.PointType;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity(name = "POINT")
@Table
public class Point extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ExpType expType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
