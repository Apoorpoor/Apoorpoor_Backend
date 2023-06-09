package com.example.apoorpoor_backend.dto.account;

import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import lombok.Data;

@Data
public class AccountSearchCondition {
    private String date;
    private String dateType;
    private AccountType accountType;
    private ExpenditureType expenditureType;
    private IncomeType incomeType;
    private String startDate;
    private String endDate;
}
