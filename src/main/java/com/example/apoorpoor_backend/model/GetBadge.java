package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "GET_BADGE")
@NoArgsConstructor
@Getter
@AllArgsConstructor
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
        badge.getGetBadgeList().add(this);
        beggar.getGetBadgeList().add(this);
    }
}
