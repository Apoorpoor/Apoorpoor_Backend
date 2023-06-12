package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.example.apoorpoor_backend.controller.SseController.sseEmitters;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public void notifyGetBadgeEvent(User user, Beggar beggar, String expenditureTitle) {
        String username = user.getUsername();
        //Long beggarId =beggar.getId();
        String nickName = beggar.getNickname();

        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"nickName\":\"" + nickName + "\", " + "\"msg :" + expenditureTitle + "뱃지를 획득 하였습니다!" + "\","
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\" }"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }

    public void notifyTestEvent(User user) {
        String username = user.getUsername();
        //Long beggarId =beggar.getId();

        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("test").data("sse test 알람!!"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }
}