package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.chat.BadWordFiltering;
import com.example.apoorpoor_backend.dto.chat.ChatDto;
import com.example.apoorpoor_backend.dto.chat.ChatImageDto;
import com.example.apoorpoor_backend.dto.chat.ChatListDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Chat;
import com.example.apoorpoor_backend.model.Image;
import com.example.apoorpoor_backend.model.enumType.MessageType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.chat.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    private final BeggarRepository beggarRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate msgOperation;
    private final BadWordFiltering badWordFiltering;
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
        ChatDto newChatDto = badWordFiltering.change(chatDto);
        Chat chat = Chat.builder()
                .sender(newChatDto.getSender())
                .message(newChatDto.getMessage())
                .level(newChatDto.getLevel())
                .beggar(beggar)
                .type(type)
                .build();
        chatRepository.save(chat);
        msgOperation.convertAndSend("/sub/chat/room", newChatDto);
    }

    public List<ChatDto> saveChatList () {
        List<Chat> chatList = chatRepository.findAll();
        List<ChatDto> chatDtoList = new ArrayList<>();

        for (Chat chat : chatList) {
            ChatDto chatDto = ChatDto.builder()
                    .type(chat.getType())
                    .beggar_id(chat.getBeggar().getId())
                    .sender(chat.getSender())
                    .message(chat.getMessage())
                    .level(chat.getLevel())
                    .build();
            chatDtoList.add(chatDto);
        }
        return chatDtoList;
    }
    public List<ChatImageDto> saveImageList(){
    }

    public Beggar beggarCheck(Long beggar_id) {
        return beggarRepository.findById(beggar_id).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }
}