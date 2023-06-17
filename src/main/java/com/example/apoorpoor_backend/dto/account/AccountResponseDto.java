package com.example.apoorpoor_backend.dto.account;

import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.Balance;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AccountResponseDto {
    private Long id;
    private String title;
    private Long userId;
    private List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList;
    private Balance balance;

}
