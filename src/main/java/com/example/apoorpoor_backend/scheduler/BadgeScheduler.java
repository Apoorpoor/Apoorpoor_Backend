package com.example.apoorpoor_backend.scheduler;

import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.service.BeggarService;
import com.example.apoorpoor_backend.service.SocialService;
import com.example.apoorpoor_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
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

    // 매일 오후 18마다 실행 "0 0 18 * * *"
    // 매달 1일 00시에 실행 "0 0 0 1 * *"
    //@Scheduled(cron = "10 * * * * *")

    // 뱃지 부여 크론 잡
    @Scheduled(cron = "0 0 0 1 * *")
    public void grantBadge(){
        // 해당 월의 지출내역에 따른 뱃지 부여
        List<User> userList = userService.getUserList();

        beggarService.resetBadge(); // 가지고 있는 뱃지 모두 리셋

        for (User user : userList) {
            beggarService.badgeCheck(user); // 새로운 뱃지 부여
            //beggarService.addPoints(user); //뱃지 1개당 20포인트
            log.info(LocalDate.now()+"cron job 완료");
        }
    }

    // 매달 나이대 별 소비/저축 평균 업데이트
    @Scheduled(cron = "0 0 0 1 * *")
    public void social(){
        // 해당 월의 나이대 별 소비/저축 업데이트
        socialService.updatePercent();


        // 랭킹 - 절약 푸어 / 플렉스 푸어
        socialService.updateRank();

    }
}