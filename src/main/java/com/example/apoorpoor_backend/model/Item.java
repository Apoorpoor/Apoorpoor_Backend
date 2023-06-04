package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "ITEM")
@NoArgsConstructor
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

    public Item(Long itemNum, String itemName, Long levelLimit, String itemType, Beggar beggar) {
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.levelLimit = levelLimit;
        this.itemType = itemType;
        this.beggar = beggar;
    }
}
