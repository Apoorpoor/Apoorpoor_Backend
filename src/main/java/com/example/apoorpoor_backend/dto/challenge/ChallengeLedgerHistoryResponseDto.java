package com.example.apoorpoor_backend.dto.challenge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChallengeLedgerHistoryResponseDto {
    List<ChallengeLedgerDto> challengeLedgerHistoryList;

}
