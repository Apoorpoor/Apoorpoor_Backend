package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.entity.Chat;
import com.example.apoorpoor_backend.entity.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ChatDto {
    private MessageType type;
    private String sender;
    private String userId;
    private String roomId;
    private String date;
    private String message;
    private String image;

     public ChatDto(Chat chat){
         this.type = chat.getType();
         this.sender = chat.getSender();
         this.message = chat.getMessage();
         this.image = chat.getImageUrl();
     }

}
