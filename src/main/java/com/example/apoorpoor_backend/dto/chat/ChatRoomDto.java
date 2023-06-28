package com.example.apoorpoor_backend.dto.chat;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRoomDto {
    private Long chatRoomId;
    private List<ChatDto> chatList;
}
