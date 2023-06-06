package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "GET_BADGE")
@NoArgsConstructor
@Getter
public class GetBadge extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "badge_id")
    private Badge badge;

    @ManyToOne
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    public GetBadge(Badge badge, Beggar beggar) {
        this.badge = badge;
        this.beggar = beggar;
        badge.getGetBadgeList().add(this); // Badge 엔티티의 getGetBadgeList에도 추가
        beggar.getGetBadgeList().add(this); // Beggar 엔티티의 getGetBadgeList에도 추가
    }
}
