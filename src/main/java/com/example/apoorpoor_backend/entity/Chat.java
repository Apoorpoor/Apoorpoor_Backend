//package com.example.apoorpoor_backend.entity;
//
//import com.example.apoorpoor_backend.dto.ChatDto;
//import com.example.apoorpoor_backend.user.User;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity(name = "TB_CHAT")
//public class Chat extends Timestamped {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Enumerated(EnumType.STRING)
//    private MessageType type;
//
//    @Column
//    private String sender;
//
//    @Column
//    private String message;
//
//    @Column(nullable = true)
//    private String imageUrl;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private User user;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "room_Id")
//    private ChatRoom room;
//
//    public Chat (ChatDto chatDto, ChatRoom room, User user, MessageType type, String imageUrl) {
//        this.sender = chatDto.getSender();
//        this.message = chatDto.getMessage();
//        this.room = room;
//        this.type = type;
//        this.imageUrl = imageUrl;
//        this.user = user;
//    }
//}