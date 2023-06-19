package com.example.apoorpoor_backend.dto.chat;

import lombok.*;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatListDto {
    private Set<Long> chatList;

    public int getChatCount() {
        return chatList.size();
    }

//    private int chatCount;
//
//    public void incrementChatCount() {
//        this.chatCount++;
//    }
//    public void decrementChatCount(){this.chatCount--;}
}
