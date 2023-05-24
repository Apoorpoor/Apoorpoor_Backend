package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.entity.Chat;
import com.example.apoorpoor_backend.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ChatDto {
    private MessageType type;
    private String sender;
    private Long beggar_id;
    private String date;
    private String message;
    private String image;
    private String roomId;

    public ChatDto (Chat chat){
        this.type = chat.getType();
        this.sender = chat.getSender();
        this.beggar_id = chat.getBeggar().getId();
        this.message = chat.getMessage();
        this.image = chat.getImageUrl();
        this.roomId = chat.getRoom().getRoomId();
    }
}
