package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.repository.badge.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static com.example.apoorpoor_backend.controller.SseController.sseEmitters;

@Service
@RequiredArgsConstructor
public class NotificationService {

    public void notifyGetBadgeEvent(Beggar beggar) {
        Long beggarId =beggar.getId();

        if(sseEmitters.containsKey(beggarId)) {
            SseEmitter sseEmitter = sseEmitters.get(beggarId);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("뱃지를 획득 하였습니다!"));
            } catch (Exception e) {
                sseEmitters.remove(beggarId);
            }
        }
    }
}