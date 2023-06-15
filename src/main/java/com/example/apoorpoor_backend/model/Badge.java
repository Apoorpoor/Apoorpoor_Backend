package com.example.apoorpoor_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "BADGE")
@Builder
@Getter
public class Badge extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @Column
    private Long badgeNum;

    @Column
    private String badgeTitle;

    @Column
    private String badgeImage;

    @JsonIgnore
    @OneToMany(mappedBy = "badge")
    private List<GetBadge> getBadgeList = new ArrayList<>();

}
