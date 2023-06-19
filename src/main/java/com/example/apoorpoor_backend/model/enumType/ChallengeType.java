package com.example.apoorpoor_backend.model.enumType;

import lombok.Getter;

@Getter
public enum ChallengeType {
    CHALLENGE_20000("2만원 챌린지", 20000L, 200L),
    CHALLENGE_50000("5만원 챌린지", 50000L, 100L),
    CHALLENGE_100000("10만원 챌린지", 100000L, 40L),
    CHALLENGE_0("무지출 챌린지", 0L, 500L);

    private final String title;
    private final Long challengeAmount;
    private final Long reward;

    ChallengeType(String title, Long challengeAmount, Long reward) {
        this.title = title;
        this.challengeAmount = challengeAmount;
        this.reward = reward;
    }

}
