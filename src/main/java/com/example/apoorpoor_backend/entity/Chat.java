package com.example.apoorpoor_backend.entity;

import com.example.apoorpoor_backend.entity.Timestamped;
import jakarta.persistence.*;

@Entity(name = "TB_CHAT")
public class Chat extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column
    private String sender;

    @Column
    private String message;

    @Column(nullable = true)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_Id")
    private ChatRoom room;


    public Chat (ChatDto chatDto, ChatRoom room, User user, MessageType type, String imageUrl) {
        this.sender = chatDto.getSender();
        this.message = chatDto.getMessage();
        this.room = room;
        this.user = user;
        this.userid = user.getUserid();
        this.type = type;
        this.profile_image = profile_image;
    }




//    {
//            "memberId":”long”,
//        "roomId": “string”,
//    }
}
