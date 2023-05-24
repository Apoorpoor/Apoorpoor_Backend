package com.example.apoorpoor_backend.entity;


import javax.persistence.*;

@Entity(name = "TB_CHATROOM")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userid;

    public ChatRoom(String roomName, String host, String userid) {

        this.userid = userid;
    }
}
