package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import com.example.apoorpoor_backend.repository.LedgerHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LedgerHistoryServiceTest {


    @Autowired
    private LedgerHistoryRepository ledgerHistoryRepository;

    @Test
    @Transactional
    @DisplayName("ledgerHistoryRepository.save() Test")
    void testSaveLedgerHistory() {
        //given
        Account account = new Account();
        String title = "Test Title";
        AccountType accountType = AccountType.INCOME;
        IncomeType incomeType = IncomeType.ALLOWANCE;
        ExpenditureType expenditureType = null;
        PaymentMethod paymentMethod = null;
        Long income = 2000000L;
        Long expenditure = 0L;
        String date = "2023-06-01";
        LedgerHistory ledgerHistory = new LedgerHistory(account, title, accountType, incomeType, expenditureType, paymentMethod, income, expenditure, date);

        //when
        ledgerHistoryRepository.save(ledgerHistory);

        //then
        Long ledgerHistoryId = ledgerHistory.getId();
        assertNotNull(ledgerHistoryId);
        LedgerHistory saveLedgerHistory = ledgerHistoryRepository.findById(ledgerHistoryId).orElse(null);
        assertNotNull(saveLedgerHistory);
        assertEquals(account, saveLedgerHistory.getAccount());
        assertEquals(title, saveLedgerHistory.getTitle());
        assertEquals(incomeType, saveLedgerHistory.getIncomeType());
        assertEquals(expenditureType, saveLedgerHistory.getExpenditureType());
        assertEquals(paymentMethod, saveLedgerHistory.getPaymentMethod());
        assertEquals(income, saveLedgerHistory.getIncome());
        assertEquals(expenditure, saveLedgerHistory.getExpenditure());
        assertEquals(date, saveLedgerHistory.getDate());
        assertEquals(ledgerHistory, saveLedgerHistory);

    }

    @Test
    @Transactional
    void createLedgerHistory() {
    }

    @Test
    void updateLedgerHistory() {
    }

    @Test
    void getLedgerHistory() {
    }

    @Test
    void deleteLedgerHistory() {
    }

    @Test
    void userCheck() {
    }

    @Test
    void accountCheck() {
    }

    @Test
    void ledgerHistoryCheck() {
    }
}