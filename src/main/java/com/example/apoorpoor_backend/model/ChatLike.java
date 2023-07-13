package com.example.apoorpoor_backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ChatLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    @JsonBackReference
    private Chat chat;

    public ChatLike(Chat chat, Beggar beggar) {
        this.beggar = beggar;
        this.chat = chat;
    }

}
