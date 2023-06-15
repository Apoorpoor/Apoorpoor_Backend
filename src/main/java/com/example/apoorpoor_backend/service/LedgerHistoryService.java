package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.model.*;
import com.example.apoorpoor_backend.model.enumType.*;
import com.example.apoorpoor_backend.repository.*;
import com.example.apoorpoor_backend.repository.account.AccountRepository;
import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
import com.example.apoorpoor_backend.repository.ledgerhistory.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.user.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
@Builder
public class LedgerHistoryService {

    private final LedgerHistoryRepository ledgerHistoryRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final BeggarRepository beggarRepository;
    private final BeggarService beggarService;
    private final NotificationService notificationService;

    private final Random random = new Random();

    public ResponseEntity<StatusResponseDto> createLedgerHistory(LedgerHistoryRequestDto requestDto, String username) {
        User user = userCheck(username);
        Account account = accountCheck(requestDto.getAccountId());
        Beggar beggar = beggarCheck(username);


        String title = requestDto.getTitle();
        AccountType accountType = requestDto.getAccountType();
        IncomeType incomeType = requestDto.getIncomeType();
        ExpenditureType expenditureType = requestDto.getExpenditureType();
        PaymentMethod paymentMethod = requestDto.getPaymentMethod();
        Long income = requestDto.getIncome();
        Long expenditure = requestDto.getExpenditure();
        LocalDate localDate = LocalDate.parse(requestDto.getDate());

        if(accountType == AccountType.INCOME){
            expenditureType = null;
            expenditure = 0L;
        }else if(accountType == AccountType.EXPENDITURE){
            incomeType = null;
            income = 0L;
        }else {
            paymentMethod = null;
            expenditureType = null;
            incomeType = null;
            expenditure = 0L;
            income = 0L;
        }

        LedgerHistory ledgerHistory = LedgerHistory.builder()
                .account(account)
                .title(title)
                .accountType(accountType)
                .incomeType(incomeType)
                .expenditureType(expenditureType)
                .paymentMethod(paymentMethod)
                .income(income)
                .expenditure(expenditure)
                .date(localDate)
                .build();

        ledgerHistoryRepository.save(ledgerHistory);

        Optional<Balance> findBalance = getBalance(account);

        if(findBalance.isPresent()) {
            Long incomeTotal = income + findBalance.get().getIncomeTotal();
            Long expenditureTotal = expenditure + findBalance.get().getExpenditureTotal();

            findBalance.get().update(incomeTotal, expenditureTotal);
        }else{
            Balance balance = Balance.builder()
                    .incomeTotal(income)
                    .expenditureTotal(expenditure)
                    .account(account)
                    .build();

            balanceRepository.save(balance);
        }

        ExpType expType = ExpType.FILL_LEDGER;
        if (expenditureType != null && expenditureType.equals(ExpenditureType.SAVINGS)) {
            expType = ExpType.BEST_SAVER;
        }

        MentManager mentManager = new MentManager();
        String randomMENT = mentManager.getMent(accountType, incomeType, expenditureType);

        beggarService.updateExpNew(user.getUsername(), expType);

        return new ResponseEntity<>(new StatusResponseDto(randomMENT), HttpStatus.OK);
    }


    public class MentManager {
        public String getMent(AccountType accountType, IncomeType incomeType, ExpenditureType expenditureType) {

            if (accountType == AccountType.EXPENDITURE) {
                MentType mentType = getExpenditureMentType(expenditureType);
                if (mentType != null) {
                    List<String> ments = mentType.getMents();
                    if (ments != null && !ments.isEmpty()) {
                        return ments.get(random.nextInt(ments.size()));
                    }
                }
            } else {
                MentType mentType = getIncomeMentType(incomeType);
                if (mentType != null) {
                    List<String> ments = mentType.getMents();
                    if (ments != null && !ments.isEmpty()) {
                        return ments.get(random.nextInt(ments.size()));
                    }
                }
            }
            return null;
        }

        private MentType getExpenditureMentType(ExpenditureType expenditureType) {
            switch (expenditureType) {
                case UTILITY_BILL:
                    return MentType.MENT1;
                case CONDOLENCE_EXPENSE:
                    return MentType.MENT2;
                case TRANSPORTATION:
                    return MentType.MENT3;
                case COMMUNICATION_EXPENSES:
                    return MentType.MENT4;
                case INSURANCE:
                    return MentType.MENT5;
                case EDUCATION:
                    return MentType.MENT6;
                case SAVINGS:
                    return MentType.MENT7;
                case CULTURE:
                    return MentType.MENT8;
                case HEALTH:
                    return MentType.MENT9;
                case FOOD_EXPENSES:
                    return MentType.MENT10;
                case SHOPPING:
                    return MentType.MENT11;
                default:
                    return null;
            }
        }

        private MentType getIncomeMentType(IncomeType incomeType) {
            switch (incomeType) {
                case EMPLOYMENT_INCOME:
                    return MentType.MENT12;
                case BUSINESS:
                    return MentType.MENT13;
                case STOCKS:
                    return MentType.MENT14;
                case INVESTMENT:
                    return MentType.MENT15;
                case ALLOWANCE:
                    return MentType.MENT16;
                case FIXED_DEPOSIT_MATURITY:
                    return MentType.MENT17;
                case OTHER:
                    return MentType.MENT18;
                default:
                    return null;
            }
        }
    }

    public ResponseEntity<LedgerHistoryResponseDto> updateLedgerHistory(Long id, LedgerHistoryRequestDto requestDto, String username) {
        User user = userCheck(username);
        Account account = accountCheck(requestDto.getAccountId());

        String title = requestDto.getTitle();
        IncomeType incomeType = requestDto.getIncomeType();
        ExpenditureType expenditureType = requestDto.getExpenditureType();
        Long income = requestDto.getIncome();
        Long expenditure = requestDto.getExpenditure();
        AccountType accountType = requestDto.getAccountType();
        PaymentMethod paymentMethod = requestDto.getPaymentMethod();
        LocalDate localDate = LocalDate.parse(requestDto.getDate());

        if(accountType == AccountType.INCOME){
            expenditureType = null;
            expenditure = 0L;
        }else if(accountType == AccountType.EXPENDITURE){
            incomeType = null;
            income = 0L;
        }else {
            paymentMethod = null;
            expenditureType = null;
            incomeType = null;
            expenditure = 0L;
            income = 0L;
        }

        LedgerHistory ledgerHistory = ledgerHistoryCheck(id);

        Optional<Balance> findBalance = getBalance(account);

        if(findBalance.isPresent()) {
            Long incomeTotal = income + findBalance.get().getIncomeTotal()-ledgerHistory.getIncome();
            Long expenditureTotal = expenditure + findBalance.get().getExpenditureTotal()-ledgerHistory.getExpenditure();

            findBalance.get().update(incomeTotal, expenditureTotal);
        }else{
            Balance balance = Balance.builder()
                    .incomeTotal(income)
                    .expenditureTotal(expenditure)
                    .account(account)
                    .build();

            balanceRepository.save(balance);
        }

        LedgerHistoryResponseDto responseDto = LedgerHistoryResponseDto.builder()
                .title(title)
                .accountType(accountType)
                .incomeType(incomeType)
                .expenditureType(expenditureType)
                .paymentMethod(paymentMethod)
                .income(income)
                .expenditure(expenditure)
                .date(localDate.toString())
                .build();
        ledgerHistory.update(responseDto);

        return ResponseEntity.ok(responseDto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<LedgerHistoryResponseDto> getLedgerHistory(Long id, String username) {
        User user = userCheck(username);
        LedgerHistory ledgerHistory = ledgerHistoryCheck(id);

        LedgerHistoryResponseDto ledgerHistoryResponseDto = LedgerHistoryResponseDto.builder()
                .id(ledgerHistory.getId())
                .title(ledgerHistory.getTitle())
                .accountType(ledgerHistory.getAccountType())
                .incomeType(ledgerHistory.getIncomeType())
                .expenditureType(ledgerHistory.getExpenditureType())
                .paymentMethod(ledgerHistory.getPaymentMethod())
                .income(ledgerHistory.getIncome())
                .expenditure(ledgerHistory.getExpenditure())
                .date(String.valueOf(ledgerHistory.getDate()))
                .build();

        return new ResponseEntity<>(ledgerHistoryResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDto> deleteLedgerHistory(Long id, String username){
        User user = userCheck(username);

        LedgerHistory ledgerHistory = ledgerHistoryCheck(id);

        Optional<Balance> findBalance = getBalance(ledgerHistory.getAccount());

        if(findBalance.isPresent()) {
            Long incomeTotal = Optional.of(findBalance.get().getIncomeTotal()).orElse(0L)-Optional.ofNullable(ledgerHistory.getIncome()).orElse(0L);
            Long expenditureTotal = Optional.of(findBalance.get().getExpenditureTotal()).orElse(0L)-Optional.ofNullable(ledgerHistory.getExpenditure()).orElse(0L);

            findBalance.get().update(incomeTotal, expenditureTotal);
        }

        ledgerHistoryRepository.deleteById(id);
        return new ResponseEntity<>(new StatusResponseDto("거래내역 삭제 성공"), HttpStatus.OK);
    }


    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

    public Account accountCheck(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 가계부입니다.")
        );
    }

    public LedgerHistory ledgerHistoryCheck(Long ledgerHistoryId){
        return ledgerHistoryRepository.findById(ledgerHistoryId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 거래내역입니다.")
        );
    }

    private Optional<Balance> getBalance(Account account) {
        return balanceRepository.findByAccountId(account.getId());
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }

}
