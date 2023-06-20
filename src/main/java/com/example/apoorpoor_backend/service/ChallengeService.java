package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.challenge.ChallengeStampResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Challenge;
import com.example.apoorpoor_backend.model.enumType.ChallengeType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.challenge.ChallengeRepository;
import com.example.apoorpoor_backend.service.event.ChallengeButtonClickEvent;
import com.example.apoorpoor_backend.service.event.ChallengeEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeService {
    private final BeggarRepository beggarRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeEventHandler challengeEventHandler;
    private final NotificationService notificationService;

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
        Challenge challenge = challengeCheck(beggar.getId());

        ChallengeStampResponseDto challengeStampResponseDto = ChallengeStampResponseDto.builder()
                .isMonday(challenge.getIsMonday())
                .isTuesday(challenge.getIsTuesday())
                .isWednesday(challenge.getIsWednesday())
                .isThursday(challenge.getIsThursday())
                .isFriday(challenge.getIsFriday())
                .isSaturday(challenge.getIsSaturday())
                .isSunday(challenge.getIsSunday())
                .build();

        return new ResponseEntity<>(challengeStampResponseDto, HttpStatus.OK);
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

    public Boolean expenditureCheck(Long weekExpenditure, Long challengeAmount) {
        return challengeAmount >= weekExpenditure;
    }

    public void challengeResult(Challenge challenge) {
        if(challenge.getSuccessCount() >= 7) {
            challenge.getBeggar().updatePoint(challenge.getChallengeType().getReward());
            notificationService.notifyFinishChallengeSuccessEvent(challenge);
        } else {
            notificationService.notifyFinishChallengeFailureEvent(challenge);
        }
    }

    public Challenge challengeCheck(Long beggarId) {
        return challengeRepository.findChallengeByBeggarId(beggarId).orElseThrow(
                () -> new IllegalArgumentException("챌린지에 도전하지 않았습니다.")
        );
    }

    public void resetChallenge() {
        challengeRepository.deleteAll();
    }
}
