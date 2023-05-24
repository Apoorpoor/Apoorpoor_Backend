package com.example.apoorpoor_backend.entity;

import com.example.apoorpoor_backend.dto.ChatDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "TB_Chat")
@Getter
@NoArgsConstructor
 public class Chat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String sender;

    @Column
    private String message;

    @Column
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beggar_id")
    private Beggar beggar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_Id")
    private ChatRoom room;

    public Chat (ChatDto chatDto, Beggar beggar, MessageType type) {
       this.sender = chatDto.getSender();
       this.message = chatDto.getMessage();
       this.beggar = beggar;
       this.type = type;
    }

}
