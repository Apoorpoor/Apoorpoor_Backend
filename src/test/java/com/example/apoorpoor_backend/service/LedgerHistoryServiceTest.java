package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.AccountRequestDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.UserRoleEnum;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import com.example.apoorpoor_backend.repository.AccountRepository;
import com.example.apoorpoor_backend.repository.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LedgerHistoryServiceTest {

    @Autowired
    private LedgerHistoryRepository ledgerHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void beforeEach() {
        ledgerHistoryRepository.deleteAll();
    }

    @Test
    @DisplayName("ledgerHistoryRepository.save() Test")
    void testSaveLedgerHistoryTest() {
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
    @DisplayName("accountType 은 INCOME 일때 Test")
    void createIncomeLedgerHistoryTest() {
        //given
        Account account = new Account();
        String title = "INCOME Test Title";
        AccountType accountType = AccountType.INCOME;
        IncomeType incomeType = IncomeType.ALLOWANCE;
        ExpenditureType expenditureType = null;
        PaymentMethod paymentMethod = null;
        Long income = 2000000L;
        Long expenditure = 0L;
        String date = "2023-06-01";

        if (accountType == AccountType.INCOME) {
            expenditureType = null;
            paymentMethod = null;
            expenditure = 0L;
        } else if (accountType == AccountType.EXPENDITURE) {
            incomeType = null;
            income = 0L;
        } else {
            paymentMethod = null;
            expenditureType = null;
            incomeType = null;
            expenditure = 0L;
            income = 0L;
        }

        //when
        LedgerHistory ledgerHistory = new LedgerHistory(account, title, accountType, incomeType, expenditureType, paymentMethod, income, expenditure, date);

        //then
        assertEquals(account, ledgerHistory.getAccount());
        assertEquals(title, ledgerHistory.getTitle());
        assertEquals(accountType, ledgerHistory.getAccountType());
        assertEquals(incomeType, ledgerHistory.getIncomeType());
        assertNull(ledgerHistory.getExpenditureType());
        assertNull(ledgerHistory.getPaymentMethod());
        assertEquals(income, ledgerHistory.getIncome());
        assertEquals(0L, ledgerHistory.getExpenditure());
        assertEquals(date, ledgerHistory.getDate());
        assertTrue(ledgerHistory.getDate() instanceof String);
    }

    @Test
    @DisplayName("accountType 은 EXPENDITURE 일때 Test")
    void createExpenditureLedgerHistoryTest() {
        //given
        Account account = new Account();
        String title = "EXPENDITURE Title";
        AccountType accountType = AccountType.EXPENDITURE;
        IncomeType incomeType = null;
        ExpenditureType expenditureType = ExpenditureType.EDUCATION;
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
        Long income = 0L;
        Long expenditure = 300000L;
        String date = "2023-06-01";

        if (accountType == AccountType.INCOME) {
            expenditureType = null;
            paymentMethod = null;
            expenditure = 0L;
        } else if (accountType == AccountType.EXPENDITURE) {
            incomeType = null;
            income = 0L;
        } else {
            paymentMethod = null;
            expenditureType = null;
            incomeType = null;
            expenditure = 0L;
            income = 0L;
        }

        //when
        LedgerHistory ledgerHistory = new LedgerHistory(account, title, accountType, incomeType, expenditureType, paymentMethod, income, expenditure, date);

        //then
        assertEquals(account, ledgerHistory.getAccount());
        assertEquals(title, ledgerHistory.getTitle());
        assertEquals(accountType, ledgerHistory.getAccountType());
        assertNull(ledgerHistory.getIncomeType());
        assertEquals(expenditureType, ledgerHistory.getExpenditureType());
        assertEquals(paymentMethod, ledgerHistory.getPaymentMethod());
        assertEquals(0L, ledgerHistory.getIncome());
        assertEquals(expenditure, ledgerHistory.getExpenditure());
        assertEquals(date, ledgerHistory.getDate());
        assertTrue(ledgerHistory.getDate() instanceof String);
    }

    @Test
    @DisplayName("accountType이 INCOME, EXPENDITURE 이 아닐 때 Test")
    void createExceptionLedgerHistoryTest() {
        //given
        Account account = new Account();
        String title = "EXPENDITURE Title";
        AccountType accountType = null;
        IncomeType incomeType = IncomeType.ALLOWANCE;
        ExpenditureType expenditureType = ExpenditureType.EDUCATION;
        PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
        Long income = 20000L;
        Long expenditure = 300000L;
        String date = "2023-06-01";

        if (accountType == AccountType.INCOME) {
            paymentMethod = null;
            expenditureType = null;
            expenditure = 0L;
        } else if (accountType == AccountType.EXPENDITURE) {
            incomeType = null;
            income = 0L;
        } else {
            paymentMethod = null;
            expenditureType = null;
            incomeType = null;
            expenditure = 0L;
            income = 0L;
        }

        //when
        LedgerHistory ledgerHistory = new LedgerHistory(account, title, accountType, incomeType, expenditureType, paymentMethod, income, expenditure, date);

        //then
        assertEquals(account, ledgerHistory.getAccount());
        assertEquals(title, ledgerHistory.getTitle());
        assertEquals(accountType, ledgerHistory.getAccountType());
        assertNull(ledgerHistory.getIncomeType());
        assertNull(ledgerHistory.getExpenditureType());
        assertNull(ledgerHistory.getPaymentMethod());
        assertEquals(0L, ledgerHistory.getIncome());
        assertEquals(0L, ledgerHistory.getExpenditure());
        assertEquals(date, ledgerHistory.getDate());
        assertTrue(ledgerHistory.getDate() instanceof String);
        assertNotEquals(IncomeType.ALLOWANCE, ledgerHistory.getIncomeType());
        assertNotEquals(ExpenditureType.EDUCATION, ledgerHistory.getExpenditureType());
        assertNotEquals(PaymentMethod.CREDIT_CARD, ledgerHistory.getPaymentMethod());
    }

    @Test
    @DisplayName("ledgerHistory.update() Test")
    void updateLedgerHistoryTest() {
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

        String modTitle = "Mod Test Title";
        AccountType modAccountType = AccountType.EXPENDITURE;
        IncomeType modIncomeType = IncomeType.STOCKS;
        ExpenditureType modExpenditureType = ExpenditureType.OTHER;
        PaymentMethod modPaymentMethod = PaymentMethod.CASH;
        Long modIncome = 5000000L;
        Long modExpenditure = 30000L;
        String modDate = "1993-06-02";

        if (modAccountType == AccountType.INCOME) {
            modExpenditureType = null;
            modPaymentMethod = null;
            modExpenditure = 0L;
        } else if (modAccountType == AccountType.EXPENDITURE) {
            modIncomeType = null;
            modIncome = 0L;
        } else {
            modPaymentMethod = null;
            modExpenditureType = null;
            modIncomeType = null;
            modExpenditure = 0L;
            modIncome = 0L;
        }

        LedgerHistoryResponseDto responseDto = LedgerHistoryResponseDto.builder()
                .title(modTitle)
                .accountType(modAccountType)
                .incomeType(modIncomeType)
                .expenditureType(modExpenditureType)
                .paymentMethod(modPaymentMethod)
                .income(modIncome)
                .expenditure(modExpenditure)
                .date(modDate)
                .build();

        //when
        ledgerHistory.update(responseDto);

        //then
        assertEquals(account, ledgerHistory.getAccount());
        assertEquals(modTitle, ledgerHistory.getTitle());
        assertEquals(modAccountType, ledgerHistory.getAccountType());
        assertNull(ledgerHistory.getIncomeType());
        assertEquals(modExpenditure, ledgerHistory.getExpenditure());
        assertEquals(modPaymentMethod, ledgerHistory.getPaymentMethod());
        assertEquals(0L, ledgerHistory.getIncome());
        assertEquals(modExpenditure, ledgerHistory.getExpenditure());
        assertEquals(modDate, ledgerHistory.getDate());
    }

    @Test
    @DisplayName("조회")
    void getLedgerHistoryTest() {
        //given
        LedgerHistory ledgerHistory = new LedgerHistory();
        ledgerHistoryRepository.save(ledgerHistory);

        Long ledgerHistoryId = ledgerHistory.getId();

        //when
        Optional<LedgerHistory> findLedgerHistory = ledgerHistoryRepository.findById(ledgerHistoryId);

        //then
        assertTrue(findLedgerHistory.isPresent());
        assertEquals(ledgerHistoryId, findLedgerHistory.get().getId());
    }

    @Test
    @DisplayName("delete LedgerHistory Test")
    void deleteLedgerHistoryTest() {
        //given
        User user = User.builder()
                .username("kakao21232212258")
                .kakaoId(21232212258L)
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);

        //when
        userRepository.delete(user);

        //then
        Optional<User> deleteUser = userRepository.findByUsername(user.getUsername());
        assertNull(deleteUser.orElse(null));
    }

    @Test
    @DisplayName("userSuccessCheck Test")
    void userSuccessCheckTest() {
        //given
        User user = User.builder()
                .username("kakao21232212258")
                .kakaoId(21232212258L)
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);

        String findUsername = user.getUsername();

        //when
        User findUser = userRepository.findByUsername(findUsername).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );

        //then
        assertEquals(user, findUser);
    }

    @Test
    @DisplayName("userFailureCheck Test")
    void userFailureCheckTest() {
        //given
        User user = User.builder()
                .username("kakao21232212258")
                .kakaoId(21232212258L)
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);

        String wrongUsername = "kakao23232456";

        //when&then
        assertThrows(IllegalArgumentException.class, () -> {
            userRepository.findByUsername(wrongUsername).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
            );
        });
    }

    @Test
    @DisplayName("accountSuccessCheck Test")
    void accountSuccessCheckTest() {
        //given
        User user = User.builder()
                .username("kakao21232212258")
                .kakaoId(21232212258L)
                .role(UserRoleEnum.USER)
                .build();

        AccountRequestDto accountRequestDto = AccountRequestDto.builder()
                .title("Test Account Title")
                .build();

        Account account = new Account(accountRequestDto, user);

        accountRepository.save(account);

        Long accountId = account.getId();

        //when
        Account findAccount = accountRepository.findById(accountId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 가계부입니다.")
        );

        //then
        assertEquals(account, findAccount);
    }

    @Test
    @DisplayName("accountFailureCheck Test")
    void accountFailureCheckTest() {
        //given
        Long accountId = 1L;

        //when&then
        assertThrows(IllegalArgumentException.class, () -> {
            accountRepository.findById(accountId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 가계부입니다.")
            );
        });
    }

    @Test
    @DisplayName("ledgerHistorySuccessCheck Test")
    void ledgerSuccessHistoryCheckTest() {
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

        ledgerHistoryRepository.save(ledgerHistory);

        Long ledgerHistoryId = ledgerHistory.getId();

        //when
        LedgerHistory findLedgerHistory = ledgerHistoryRepository.findById(ledgerHistoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 거래내역입니다.")
        );

        //then
        assertEquals(ledgerHistory, findLedgerHistory);
    }

    @Test
    @DisplayName("ledgerHistoryFailureCheck Test")
    void ledgerFailureHistoryCheckTest() {
        //given
        Long ledgerHistoryId = 1L;

        //when&then
        assertThrows(IllegalArgumentException.class, () -> {
            ledgerHistoryRepository.findById(ledgerHistoryId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 가계부입니다.")
            );
        });

    }
}