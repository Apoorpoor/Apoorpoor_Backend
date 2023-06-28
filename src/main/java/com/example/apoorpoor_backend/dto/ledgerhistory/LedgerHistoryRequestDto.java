package com.example.apoorpoor_backend.dto.ledgerhistory;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LedgerHistoryRequestDto {
    @NotNull(message = "accountId")
    private Long accountId;

    @NotEmpty(message = "title")
    private String title;

    @NotNull(message = "accountType")
    private AccountType accountType;

    private IncomeType incomeType;
    private ExpenditureType expenditureType;
    private PaymentMethod paymentMethod;
    private Long income;
    private Long expenditure;

    @NotEmpty(message = "date")
    private String date;

}
