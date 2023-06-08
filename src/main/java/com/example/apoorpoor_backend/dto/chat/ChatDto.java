package com.example.apoorpoor_backend.dto.chat;

import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.enumType.MessageType;
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
    private Long beggar_id;
    private String sender;
    private String date;
    private String message;
    private String image;
    private Long userId; //

    public ChatDto(Chat chat){
        this.type = chat.getType();
        this.beggar_id = chat.getBeggar().getId();
        this.sender = chat.getSender();
        this.message = chat.getMessage();
        this.userId = chat.getBeggar().getUser().getId(); //
    }
}
