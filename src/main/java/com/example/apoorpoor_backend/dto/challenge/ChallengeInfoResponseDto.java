package com.example.apoorpoor_backend.dto.challenge;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChallengeInfoResponseDto {
    String challengeTitle;
    String startTime;
}
