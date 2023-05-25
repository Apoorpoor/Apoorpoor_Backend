package com.example.apoorpoor_backend.dto;

import com.example.apoorpoor_backend.entity.Balance;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LedgerListResponseDto {
    private List<LedgerResponseDto> ledgerResponseDtoList;
    private Balance balance;

    public LedgerListResponseDto(List<LedgerResponseDto> ledgerResponseDtoList, Balance balance) {
        this.ledgerResponseDtoList = ledgerResponseDtoList;
        this.balance = balance;
    }
}
