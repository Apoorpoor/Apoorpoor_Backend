package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity(name = "ITEM")
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long itemNum;

    @Column
    private String itemName;

    @Column
    private Long levelLimit;

    @Column
    private String itemType;

    @ManyToOne
    @JoinColumn(name ="beggar_id")
    private Beggar beggar;

}
