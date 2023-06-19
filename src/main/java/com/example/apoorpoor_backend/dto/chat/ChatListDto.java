package com.example.apoorpoor_backend.dto.chat;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
public class ChatListDto {
    private Long beggarId;
    private String sender;
    private Long userId;
    private Long level;
    public ChatListDto(Long beggarId, String sender, Long userId, Long level) {
        this.beggarId = beggarId;
        this.sender = sender;
        this.userId = userId;
        this.level = level;
    }
}
