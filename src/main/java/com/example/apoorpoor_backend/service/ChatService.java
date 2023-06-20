package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.dto.chat.ChatListDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChatService{

    private final BeggarRepository beggarRepository;
    private final ChatRepository chatRepository;
    private final Map<Long, ChatListDto> chatParticipantsMap = new HashMap<>();

    public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("beggar_id", chatDto.getBeggar_id());
        headerAccessor.getSessionAttributes().put("nickName", chatDto.getSender());
        headerAccessor.getSessionAttributes().put("userId", chatDto.getUserId());
        chatDto.setMessage(chatDto.getSender() + "님이 입장하셨습니다.");

        ChatListDto chatListDto = new ChatListDto(
                chatDto.getBeggar_id(),
                chatDto.getSender(),
                chatDto.getUserId(),
                chatDto.getLevel()
        );
        addChatParticipant(chatListDto);
        return chatDto;
    }

    public void addChatParticipant(ChatListDto participant) {
        chatParticipantsMap.put(participant.getBeggarId(), participant);
    }

    public List<ChatListDto> getChatParticipants() {
        return new ArrayList<>(chatParticipantsMap.values());
    }

    public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
        String nickName = (String) headerAccessor.getSessionAttributes().get("nickName");
        Long beggar_id = (Long) headerAccessor.getSessionAttributes().get("beggar_id");
        Long userId = (Long) headerAccessor.getSessionAttributes().get("userId");
        ChatDto chatDto = ChatDto.builder()
                .type(MessageType.LEAVE)
                .sender(nickName)
                .beggar_id(beggar_id)
                .level(1L)
                .message(nickName + "님이 퇴장하셨습니다.")
                .userId(userId)
                .build();
        removeChatParticipant(beggar_id);
        return chatDto;
    }

    public void removeChatParticipant(Long beggar_id) {
        chatParticipantsMap.remove(beggar_id);
    }


    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
        Beggar beggar = beggarCheck(chatDto.getBeggar_id());
        MessageType type = MessageType.TALK;
        Chat chat = Chat.builder()
                .sender(chatDto.getSender())
                .message(chatDto.getMessage())
                .beggar(beggar)
                .type(type)
                .build();
        chatRepository.save(chat);
    }

    public Beggar beggarCheck(Long beggar_id) {
        return beggarRepository.findById(beggar_id).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}