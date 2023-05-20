package com.example.apoorpoor_backend.chat.entity;

import com.example.apoorpoor_backend.entity.Timestamped;
import jakarta.persistence.*;

@Entity(name = "TB_CHAT")
public class Chat extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private MessageType messageType;

    @Column
    private String sender;

    @Column
    private String message;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;





//    {
//            "memberId":”long”,
//        "roomId": “string”,
//    }
}
