package com.example.apoorpoor_backend.dto.ledgerhistory;

import com.example.apoorpoor_backend.model.Balance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LedgerHistoryListResponseDto {
    private List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList;
    private Balance balance;

    public LedgerHistoryListResponseDto(List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList, Balance balance) {
        this.ledgerHistoryResponseDtoList = ledgerHistoryResponseDtoList;
        this.balance = balance;
    }
}