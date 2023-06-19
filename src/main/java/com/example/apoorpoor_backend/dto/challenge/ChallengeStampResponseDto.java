package com.example.apoorpoor_backend.dto.challenge;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChallengeStampResponseDto {
    private Boolean isMonday;
    private Boolean isTuesday;
    private Boolean isWednesday;
    private Boolean isThursday;
    private Boolean isFriday;
    private Boolean isSaturday;
    private Boolean isSunday;

}
