package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.challenge.ChallengeInfoResponseDto;
import com.example.apoorpoor_backend.dto.challenge.ChallengeLedgerDto;
import com.example.apoorpoor_backend.dto.challenge.ChallengeLedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.challenge.ChallengeStampResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Challenge;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.Point;
import com.example.apoorpoor_backend.model.enumType.ChallengeType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.challenge.ChallengeRepository;
import com.example.apoorpoor_backend.repository.ledgerhistory.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.shop.PointRepository;
import com.example.apoorpoor_backend.service.event.ChallengeButtonClickEvent;
import com.example.apoorpoor_backend.service.event.ChallengeEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeService {
    private final BeggarRepository beggarRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeEventHandler challengeEventHandler;
    private final NotificationService notificationService;
    private final LedgerHistoryRepository ledgerHistoryRepository;
    private final PointRepository pointRepository;

    public ResponseEntity<String> createChallenge(ChallengeType challengeType, String username) {
        Beggar beggar = beggarCheck(username);

        challengeClickEvent(beggar, challengeType.getTitle());

        Challenge newChallenge = Challenge.builder()
                .username(username)
                .beggar(beggar)
                .challengeType(challengeType)
                .title(challengeType.getTitle())
                .challengeAmount(challengeType.getChallengeAmount())
                .weekExpenditure(0L)
                .build();

        challengeRepository.save(newChallenge);


        notificationService.notifyStartChallengeEvent(username, challengeType.getTitle());

        return new ResponseEntity<>(challengeType.getTitle() + " 도전!!", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<ChallengeStampResponseDto> getChallengeStamp(String username) {
        Beggar beggar = beggarCheck(username);

        ChallengeStampResponseDto challengeStampResponseDto = ChallengeStampResponseDto.builder()
                .successCount(beggar.getSuccessCount())
                .build();

        return new ResponseEntity<>(challengeStampResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<ChallengeInfoResponseDto> getChallengeInfo(String username) {
        Beggar beggar = beggarCheck(username);

        String challengeTitle = beggar.getChallengeTitle();

        ChallengeInfoResponseDto challengeInfoResponseDto = ChallengeInfoResponseDto.builder()
                .challengeTitle(challengeTitle)
                .build();

        return new ResponseEntity<>(challengeInfoResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<ChallengeLedgerHistoryResponseDto> getChallengeLedgerHistory(String username) {
        Beggar beggar = beggarCheck(username);
        Challenge challenge = challengeCheck(beggar.getId());

        List<LedgerHistory> findLedgerHistoryList = ledgerHistoryRepository.findAllByChallengeId(challenge.getId());
        List<ChallengeLedgerDto> challengeLedgerDtoList = new ArrayList<>();

        for (LedgerHistory ledgerHistory : findLedgerHistoryList) {
            ChallengeLedgerDto challengeLedgerDto = ChallengeLedgerDto.builder()
                    .title(ledgerHistory.getTitle())
                    .expenditureType(ledgerHistory.getExpenditureType())
                    .expenditure(ledgerHistory.getExpenditure())
                    .date(ledgerHistory.getDate())
                    .build();

            challengeLedgerDtoList.add(challengeLedgerDto);
        }

        ChallengeLedgerHistoryResponseDto challengeLedgerHistoryResponseDto = ChallengeLedgerHistoryResponseDto.builder()
                .challengeLedgerHistoryList(challengeLedgerDtoList)
                .build();
        return new ResponseEntity<>(challengeLedgerHistoryResponseDto, HttpStatus.OK);
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }

    public void challengeClickEvent(Beggar beggar, String challengeTitle) {
        ChallengeButtonClickEvent event = new ChallengeButtonClickEvent(beggar, challengeTitle);
        challengeEventHandler.handleChallengeButtonClick(event);
    }

    public List<Challenge> getChallengeList() {
        return challengeRepository.findAll();
    }

    public void challengeResult(Challenge challenge, Long weekExpenditure, Long challengeAmount) {
        Beggar beggar = challenge.getBeggar();

        if (challengeAmount >= weekExpenditure) {
            beggar.updateSuccessCount();
            challenge.updateSuccessStatus(true);
            if (beggar.getSuccessCount() >= 10) {
                String description = "stamp 10개 수집";
                Long point = 100L;
                beggar.updatePoint(point);
                updateRecordPoint(description, point, beggar);

                notificationService.notifyCollectStampComplete(challenge);

                beggar.updateResetSuccessCount();

            }
            notificationService.notifyFinishChallengeSuccessEvent(challenge);
        } else {
            challenge.updateSuccessStatus(false);
            notificationService.notifyFinishChallengeFailureEvent(challenge);
        }
        beggar.resetChallenge();
    }

    public Challenge challengeCheck(Long beggarId) {
        return challengeRepository.findChallengeBySuccessStatusIsNullAndBeggarId(beggarId).orElseThrow(
                () -> new IllegalArgumentException("챌린지에 도전하지 않았습니다.")
        );
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
