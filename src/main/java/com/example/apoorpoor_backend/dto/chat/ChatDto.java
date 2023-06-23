package com.example.apoorpoor_backend.dto.chat;

import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import lombok.*;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private MessageType type;
    private Long beggar_id;
    private String sender;
    private String date;
    private String message;
    private String image;
    private Long userId;
    private Long level;
    private Long chatRoomId = 1L;

    public static ChatDto fromChat(Chat chat) {
        ChatDto chatDto = new ChatDto();
        chatDto.setType(chat.getType());
        chatDto.setBeggar_id(chat.getBeggar_id());
        chatDto.setUserId(chat.getUserId());
        chatDto.setSender(chat.getSender());
        chatDto.setMessage(chat.getMessage());
        chatDto.setLevel(chat.getLevel());
        chatDto.setChatRoomId(1L);
        chatDto.setDate(chat.getDate());
        chatDto.setImage(chat.getImage());
        return chatDto;
    }
}