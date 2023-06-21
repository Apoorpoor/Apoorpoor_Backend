package com.example.apoorpoor_backend.scheduler;

import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.service.BeggarService;
import com.example.apoorpoor_backend.service.SocialService;
import com.example.apoorpoor_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BadgeScheduler {

    private final UserService userService;
    private final BeggarService beggarService;
    private final SocialService socialService;

    @Scheduled(cron = "0 0 0 1 * *")
    public void grantBadge(){

        List<User> userList = userService.getUserList();

        beggarService.resetBadge();

        for (User user : userList) {
            beggarService.badgeCheck(user);
            log.info(LocalDate.now()+"뱃지 부여 완료");
        }
    }

    @Scheduled(cron = "0 0 0 1 * *")
    public void social(){

        socialService.updatePercent();
        log.info(LocalDate.now()+"해당 월의 나이대 별 소비/저축 업데이트 완료");
        socialService.updateRank();
        log.info(LocalDate.now()+"랭킹 - 절약 푸어 / 플렉스 푸어 업데이트 완료");
    }
}