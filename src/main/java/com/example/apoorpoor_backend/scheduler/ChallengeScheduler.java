package com.example.apoorpoor_backend.scheduler;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Challenge;
import com.example.apoorpoor_backend.model.Point;
import com.example.apoorpoor_backend.repository.shop.PointRepository;
import com.example.apoorpoor_backend.service.BeggarService;
import com.example.apoorpoor_backend.service.ChallengeService;
import com.example.apoorpoor_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class ChallengeScheduler {

    private final ChallengeService challengeService;
    private final BeggarService beggarService;
    private final NotificationService notificationService;
    private final PointRepository pointRepository;

    @Scheduled(cron = "0 0 0 * * 1")
    public void expenditureMondayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.MONDAY);
        log.info(LocalDate.now() + "challenge : 월요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 0 * * 2")
    public void expenditureTuesdayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.TUESDAY);
        log.info(LocalDate.now() + "challenge : 화요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 0 * * 3")
    public void expenditureWednesdayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.WEDNESDAY);
        log.info(LocalDate.now() + "challenge : 수요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 0 * * 4")
    public void expenditureThursdayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.THURSDAY);
        log.info(LocalDate.now() + "challenge : 목요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 0 * * 5")
    public void expenditureFridayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.FRIDAY);
        log.info(LocalDate.now() + "challenge : 금요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 0 * * 6")
    public void expenditureSaturdayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.SATURDAY);
        log.info(LocalDate.now() + "challenge : 토요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 0 * * 7")
    public void expenditureSundayCheck() {
        checkExpenditureForDayOfWeek(DayOfWeek.SUNDAY);
        log.info(LocalDate.now() + "challenge : 일요일 지출체크 완료");
    }

    @Scheduled(cron = "0 0 1 * * 7")
    public void missionCheck() {
        List<Beggar> beggarList = beggarService.getBeggarList();

        challengeService.resetChallenge();

        for (Beggar beggar : beggarList) {
            beggarService.resetChallengeTitle(beggar);
        }
        log.info(LocalDate.now() + " challenge reset 완료!");

    }

    private void checkExpenditureForDayOfWeek(DayOfWeek dayOfWeek) {
        List<Challenge> challengeList = challengeService.getChallengeList();

        for (Challenge challenge : challengeList) {
            Boolean missionResult = challengeService.expenditureCheck(challenge.getWeekExpenditure(), challenge.getChallengeAmount());
            updateMissionResultForDayOfWeek(challenge, dayOfWeek, missionResult);
            log.info(LocalDate.now() + " cron job 완료");
        }
    }

    private void updateMissionResultForDayOfWeek(Challenge challenge, DayOfWeek dayOfWeek, Boolean missionResult) {
        Long point = 5L;
        switch (dayOfWeek) {
            case MONDAY -> {
                challenge.updateIsMonday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(point);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "월요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());

                }

            }
            case TUESDAY -> {
                challenge.updateIsTuesday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(5L);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "화요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());
                }
            }
            case WEDNESDAY -> {
                challenge.updateIsWednesday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(5L);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "수요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());
                }
            }
            case THURSDAY -> {
                challenge.updateIsThursday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(5L);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "목요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());
                }
            }
            case FRIDAY -> {
                challenge.updateIsFriday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(5L);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "금요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());
                }
            }
            case SATURDAY -> {
                challenge.updateIsSaturday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(5L);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "토요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());
                }
            }
            case SUNDAY -> {
                challenge.updateIsSunday(missionResult);
                if (missionResult) {
                    challenge.getBeggar().updatePoint(5L);
                    notificationService.notifyChallengeResultEvent(challenge, point);
                    String pointDescription = "일요일 목표 달성";
                    updateRecordPoint(pointDescription, point, challenge.getBeggar());
                }
                challengeService.challengeResult(challenge);

            }
            default -> throw new IllegalArgumentException("올바르지 않은 요일입니다.");
        }
    }

    @Transactional
    public void updateRecordPoint(String pointDescription, Long point, Beggar beggar) {
        Point recordPoint = Point.builder()
                .pointDescription(pointDescription)
                .earnedPoint(point)
                .usedPoints(null)
                .beggar(beggar)
                .build();

        pointRepository.save(recordPoint);
    }


}