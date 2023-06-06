package com.example.apoorpoor_backend.scheduler;

import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.ledgerhistory.LedgerHistoryRepository;
import com.example.apoorpoor_backend.service.BeggarService;
import com.example.apoorpoor_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BadgeScheduler {

    private final UserService userService;
    private final BeggarService beggarService;
    private final LedgerHistoryRepository ledgerHistoryRepository;

    // 매일 오후 18마다 실행 "0 0 18 * * *"
    // 매달 1일 00시에 실행 "0 0 0 1 * *"
    //@Scheduled(cron = "10 * * * * *")
    public void printDate () {
        System.out.println(new Date().toString());
    }

    public void grantBadge(){
        // 해당 월의 지출내역에 따른 뱃지 부여
        List<User> userList = userService.getUserList();

        beggarService.resetBadge(); // 가지고 있는 뱃지 모두 리셋

        for (User user : userList) {
            beggarService.badgeCheck(user); // 새로운 뱃지 부여
            log.info(LocalDate.now()+"cron job 완료");
        }
    }
}