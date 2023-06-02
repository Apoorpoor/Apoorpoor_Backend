package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.BadgeType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "BADGE")
@NoArgsConstructor
public class Badge extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @Column
    private Long badgeNum;

    @Column
    private String badgeTitle;

    @OneToMany(mappedBy = "badge")
    private List<GetBadge> getBadgeList = new ArrayList<>();

    public Badge(Long badgeNum, String badgeTitle) {
        this.badgeNum = badgeNum;
        this.badgeTitle = badgeTitle;
    }
}
