package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Challenge;
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

    public void notifyGetBadgeEvent(User user, String expenditureTitle) {
        String username = user.getUsername();

        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"alarmType\":\"소비뱃지\", " +
                        "\"msg\":\"" + expenditureTitle + "\"," +
                        "\"timestamp\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\"}"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }

    public void notifyLevelUpEvent(String username, Beggar beggar) {

        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"alarmType\":\"레벨업\", " +
                        "\"msg\":\"" + beggar.getLevel() + "\"," +
                        "\"timestamp\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\"}"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }

    public void notifyStartChallengeEvent(String username, String challengeTitle) {
        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"alarmType\":\"챌린지 시작\", " +
                        "\"challengeType\":\"" + challengeTitle + "\"," +
                        "\"timestamp\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\"}"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }

    public void notifyFinishChallengeSuccessEvent(Challenge challenge) {
        String username = challenge.getUsername();
        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"alarmType\":\"챌린지 종료\", " +
                        "\"challengeType\":\"" + challenge.getTitle() + "\"," +
                        "\"challenge\":\"" + challenge.getWeekExpenditure() + "\"," +
                        "\"result\":\"" + "성공" + "\"," +
                        "\"timestamp\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\"}"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }

    public void notifyFinishChallengeFailureEvent(Challenge challenge) {
        String username = challenge.getUsername();
        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"alarmType\":\"챌린지 종료\", " +
                        "\"challengeType\":\"" + challenge.getTitle() + "\"," +
                        "\"challenge\":\"" + challenge.getWeekExpenditure() + "\"," +
                        "\"result\":\"" + "실패" + "\"," +
                        "\"point\":\"" + 0L + "\"," +
                        "\"timestamp\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\"}"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }

    public void notifyCollectStampComplete(Challenge challenge) {
        String username = challenge.getUsername();
        if(sseEmitters.containsKey(username)) {
            SseEmitter sseEmitter = sseEmitters.get(username);
            try {
                sseEmitter.send(SseEmitter.event().name("getBadge").data("{\"alarmType\":\"스템프 10개 수집\", " +
                        "\"point\":\"" + 100L + "\"," +
                        "\"timestamp\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM월 dd일 a HH시 mm분")) + "\"}"));
            } catch (Exception e) {
                sseEmitters.remove(username);
            }
        }
    }
}