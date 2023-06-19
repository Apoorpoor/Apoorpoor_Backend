package com.example.apoorpoor_backend.service.event;

import com.example.apoorpoor_backend.model.Beggar;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChallengeButtonClickEvent {
    private Beggar beggar;
    private String challengeTitle;
}
