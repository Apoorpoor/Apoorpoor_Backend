package com.example.apoorpoor_backend.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BadgeScheduler {

    // 매일 오후 18마다 실행 "0 0 18 * * *"
    // 매달 1일 00시에 실행 "0 0 0 1 * *"
    @Scheduled(cron = "10 * * * * *")
    public void printDate () {
        System.out.println(new Date().toString());
    }
}