package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.LedgerHistory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AccountResponseDto {
    private Long id;
    private String title;
    private Long userId;
    private List<LedgerHistory> ledgerHistories;

    public AccountResponseDto(Account account){
        this.id = account.getId();
        this.title = account.getTitle();
        this.userId = account.getUser().getId();
        this.ledgerHistories = account.getLedgerHistories();
    }

}
