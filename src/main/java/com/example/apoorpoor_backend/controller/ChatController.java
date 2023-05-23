package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.ChatDto;
import com.example.apoorpoor_backend.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate msgOperation;

    @MessageMapping("/pub/chat/enter")
    @SendTo("/sub/chat/room")
    public void enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Thread.sleep(500);
        ChatDto newchatdto = chatService.enterChatRoom(chatDto, headerAccessor);
        msgOperation.convertAndSend("/sub/chat/room" + chatDto.getRoomId(), newchatdto);
    }
    @MessageMapping("/pub/chat/talk")
    @SendTo("/sub/chat/room")
    public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        Thread.sleep(500);
        chatService.sendChatRoom(chatDto, headerAccessor);
        msgOperation.convertAndSend("/sub/chat/room" + chatDto.getRoomId(), chatDto);
    }
    @EventListener
    public void webSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        ChatDto chatDto = chatService.disconnectChatRoom(headerAccessor);
        msgOperation.convertAndSend("/sub/chat/room" + chatDto.getRoomId() , chatDto);
    }
    @PostMapping("/chat/image")
    public String uploadImage(@RequestParam(value = "image",required = false) MultipartFile image, @AuthenticationPrincipal UserDetails userDetails)throws IOException {
        String image_url = chatService.uploadImage(image);
        return image_url;
    }

}
