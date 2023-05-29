package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.Balance;
import com.example.apoorpoor_backend.model.LedgerHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountResponseDto {
    private Long id;
    private String title;
    private Long userId;
    private List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList;
    private Balance balance;

    public AccountResponseDto(Account account, List<LedgerHistoryResponseDto> ledgerHistories){
        this.id = account.getId();
        this.title = account.getTitle();
        this.userId = account.getUser().getId();
        this.balance = account.getBalance();
        this.ledgerHistoryResponseDtoList = ledgerHistories;
    }

    public AccountResponseDto(Account account){
        this.id = account.getId();
        this.title = account.getTitle();
        this.userId = account.getUser().getId();
        this.balance = account.getBalance();
    }

    public AccountResponseDto(Long id, String title, Long userId, List<LedgerHistoryResponseDto> ledgerHistories, Balance balance) {
        this.id = id;
        this.title = title;
        this.userId = userId;
        this.ledgerHistoryResponseDtoList = ledgerHistories;
        this.balance = balance;
    }
}
