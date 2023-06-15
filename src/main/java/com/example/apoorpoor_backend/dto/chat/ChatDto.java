package com.example.apoorpoor_backend.dto.chat;

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
}
