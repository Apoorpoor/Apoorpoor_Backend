package com.example.apoorpoor_backend.service.event;

import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.Challenge;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateChallengeLedgerEvent {
    private Beggar beggar;
    private Challenge challenge;
    private Long weekExpenditure;
}
