package com.example.apoorpoor_backend.scheduler;

import com.example.apoorpoor_backend.model.Challenge;
import com.example.apoorpoor_backend.service.BeggarService;
import com.example.apoorpoor_backend.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ChallengeScheduler {

    private final ChallengeService challengeService;
    private final BeggarService beggarService;

    @Scheduled(cron = "0 0 0 * * 1")
    public void expenditureMondayCheck() {
        checkChallengeResult();
        log.info(LocalDate.now() + "challenge : 종료");
    }

    private void checkChallengeResult() {
        List<Challenge> challengeList = challengeService.getChallengeList();

        for (Challenge challenge : challengeList) {
            challengeService.challengeResult(challenge, challenge.getWeekExpenditure(), challenge.getChallengeAmount());
        }
    }

}