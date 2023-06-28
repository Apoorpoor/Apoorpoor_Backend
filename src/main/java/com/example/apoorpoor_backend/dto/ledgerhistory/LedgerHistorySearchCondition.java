package com.example.apoorpoor_backend.dto.ledgerhistory;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import lombok.Getter;

@Getter
public class LedgerHistorySearchCondition {

    private AccountType accountType;
    private IncomeType incomeType;
    private ExpenditureType expenditureType;
    private PaymentMethod paymentMethod;
    private String date;
}
