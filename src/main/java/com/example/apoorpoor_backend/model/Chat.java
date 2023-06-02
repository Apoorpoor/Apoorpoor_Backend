package com.example.apoorpoor_backend.model;

import com.example.apoorpoor_backend.dto.ChatDto;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;

    private String message;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    public Chat (ChatDto chatDto, Beggar beggar, MessageType type) {
        this.sender = chatDto.getSender();
        this.message = chatDto.getMessage();
        this.beggar = beggar;
        this.type = type;
    }

}
