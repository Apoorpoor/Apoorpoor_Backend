package com.example.apoorpoor_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "ITEM")
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
