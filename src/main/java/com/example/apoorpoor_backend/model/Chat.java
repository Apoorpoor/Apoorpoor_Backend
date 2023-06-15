package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.model.enumType.MessageType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Builder
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String message;

    @ColumnDefault("1")
    @Column(nullable = false)
    private Long level;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;
}