package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Challenge;
import com.example.apoorpoor_backend.model.enumType.ChallengeType;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.challenge.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final BeggarRepository beggarRepository;
    private final ChallengeRepository challengeRepository;

    public ResponseEntity<String> createChallenge(ChallengeType challengeType, String username) {
        Beggar beggar = beggarCheck(username);

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
}
