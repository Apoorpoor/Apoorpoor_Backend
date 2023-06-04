package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.ItemType;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity(name = "ITEM")
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private ItemType itemType;

    @ManyToOne
    @JoinColumn(name ="beggar_id")
    private Beggar beggar;

    public Item(ItemType itemType, Beggar beggar) {
        this.itemType = itemType;
        this.beggar = beggar;
    }
}
