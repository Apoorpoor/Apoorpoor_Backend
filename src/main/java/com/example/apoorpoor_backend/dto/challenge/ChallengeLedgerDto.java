package com.example.apoorpoor_backend.dto.challenge;

import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ChallengeLedgerDto {
    private String title;
    private ExpenditureType expenditureType;
    private Long expenditure;
    private LocalDateTime date;
}
