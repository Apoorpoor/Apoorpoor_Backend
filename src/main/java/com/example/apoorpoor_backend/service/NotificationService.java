package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.model.Beggar;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.apoorpoor_backend.controller.SseController.sseEmitters;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public void notifyGetBadgeEvent(Beggar beggar, String expenditureTitle) {
        Long beggarId =beggar.getId();
        String nickName = beggar.getNickname();

        if(sseEmitters.containsKey(beggarId)) {
            SseEmitter sseEmitter = sseEmitters.get(beggarId);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"nickName\":\"" + nickName + "\", " + "\"msg :" + expenditureTitle + "뱃지를 획득 하였습니다!" + "\","
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\" }"));
            } catch (Exception e) {
                sseEmitters.remove(beggarId);
            }
        }
    }
}