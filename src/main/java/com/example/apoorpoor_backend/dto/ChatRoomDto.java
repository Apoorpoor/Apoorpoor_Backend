package com.example.apoorpoor_backend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRoomDto {
    private String roomName;
    private String imageUrl;

    public ChatRoomDto(ChatRoomDto chatRoomDto, String imageUrl){
        this.roomName = chatRoomDto.getRoomName();
        this.imageUrl = imageUrl;
    }
}
