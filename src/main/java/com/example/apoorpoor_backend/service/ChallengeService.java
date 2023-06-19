package com.example.apoorpoor_backend.service;

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
public class ChallengeService {
    private final BeggarRepository beggarRepository;
    private final ChallengeRepository challengeRepository;

    private final ChallengeEventHandler challengeEventHandler;

    public ResponseEntity<String> createChallenge(ChallengeType challengeType, String username) {
        Beggar beggar = beggarCheck(username);

        challengeClickEvent(beggar, challengeType.getTitle());

        Challenge newChallenge = Challenge.builder()
                .beggar(beggar)
                .challengeType(challengeType)
                .title(challengeType.getTitle())
                .challengeAmount(challengeType.getChallengeAmount())
                .weekExpenditure(0L)
                .build();

        challengeRepository.save(newChallenge);

        return new ResponseEntity<>(challengeType.getTitle() + " 도전!!", HttpStatus.OK);
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
        }
    }

    @Transactional
    public void resetChallenge() {
        challengeRepository.deleteAll();
    }
}
