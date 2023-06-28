package com.example.apoorpoor_backend.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChallengeEventHandler {
    @EventListener
    public void handleChallengeButtonClick(ChallengeButtonClickEvent event) {
        if(event.getBeggar().getChallengeTitle() == null) {
            event.getBeggar().updateChallenge(event.getChallengeTitle());
        } else {
            throw new IllegalArgumentException("진행중인 챌린지가 존재합니다.");
        }
    }

    @EventListener
    public void updateChallengeLedger(UpdateChallengeLedgerEvent event) {
        if(event.getChallenge() != null) {
            event.getChallenge().updateWeekExpenditure(event.getWeekExpenditure());
        }
    }
}
