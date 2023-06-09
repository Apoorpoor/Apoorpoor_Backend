package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.Balance;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.account.AccountRepository;
import com.example.apoorpoor_backend.repository.BalanceRepository;
import com.example.apoorpoor_backend.repository.ledgerhistory.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final LedgerHistoryRepository ledgerHistoryRepository;

    public ResponseEntity<StatusResponseDto> createAccount(AccountRequestDto requestDto, String username) {
        User user = userCheck(username);

        Account account = Account.builder()
                .title(requestDto.getTitle())
                .user(user)
                .build();

        accountRepository.save(account);

        return new ResponseEntity<>(new StatusResponseDto("가계부 생성 완료"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(String username){
        User user = userCheck(username);
        List<Account> accountList = accountRepository.findAllByUserIdOrderByCreatedAtDesc(user.getId());

        List<AccountResponseDto> accountResponseDtoList = new ArrayList<>();

        for (Account account : accountList) {
            List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList = new ArrayList<>();

            for (LedgerHistory ledgerHistory : account.getLedgerHistories()) {

                LedgerHistoryResponseDto ledgerHistoryResponseDto = LedgerHistoryResponseDto.builder()
                        .id(ledgerHistory.getId())
                        .title(ledgerHistory.getTitle())
                        .accountType(ledgerHistory.getAccountType())
                        .incomeType(ledgerHistory.getIncomeType())
                        .expenditureType(ledgerHistory.getExpenditureType())
                        .paymentMethod(ledgerHistory.getPaymentMethod())
                        .income(ledgerHistory.getIncome())
                        .expenditure(ledgerHistory.getExpenditure())
                        .date(ledgerHistory.getDate().toString())
                        .build();

                ledgerHistoryResponseDtoList.add(ledgerHistoryResponseDto);
            }

            AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                    .id(account.getId())
                    .title(account.getTitle())
                    .userId(account.getUser().getId())
                    .balance(account.getBalance())
                    .ledgerHistoryResponseDtoList(ledgerHistoryResponseDtoList)
                    .build();

            accountResponseDtoList.add(accountResponseDto);
        }

        return ResponseEntity.ok(accountResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<AccountResponseDto> getAccount(Long id, String username) {
        User user = userCheck(username);
        Account account = getAccount(id);

        Optional<Balance> findBalance = balanceRepository.findByAccountId(id);

        Balance balance = null;

        if(findBalance.isPresent()) balance = findBalance.get();

        List<LedgerHistory> ledgerHistoryList = account.getLedgerHistories();

        List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList = new ArrayList<>();

        for (LedgerHistory ledgerHistory : ledgerHistoryList) {

            LedgerHistoryResponseDto ledgerHistoryResponseDto = LedgerHistoryResponseDto.builder()
                    .id(ledgerHistory.getId())
                    .title(ledgerHistory.getTitle())
                    .accountType(ledgerHistory.getAccountType())
                    .incomeType(ledgerHistory.getIncomeType())
                    .expenditureType(ledgerHistory.getExpenditureType())
                    .paymentMethod(ledgerHistory.getPaymentMethod())
                    .income(ledgerHistory.getIncome())
                    .expenditure(ledgerHistory.getExpenditure())
                    .date(ledgerHistory.getDate().toString())
                    .build();


            ledgerHistoryResponseDtoList.add(ledgerHistoryResponseDto);
        }

        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(account.getId())
                .title(account.getTitle())
                .userId(account.getUser().getId())
                .ledgerHistoryResponseDtoList(ledgerHistoryResponseDtoList)
                .balance(balance)
                .build();

        return new ResponseEntity<>(accountResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<AccountResponseDto> updateAccount(Long id, AccountRequestDto requestDto, String username) {
        User user = userCheck(username);
        Account account = getAccount(id);
        account.update(requestDto);
        
        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(account.getId())
                .title(account.getTitle())
                .userId(account.getUser().getId())
                .balance(account.getBalance())
                .build();
        
        return ResponseEntity.ok(accountResponseDto);
    }

    public ResponseEntity<StatusResponseDto> deleteAccount(Long id, String username) {
        User user = userCheck(username);
        accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 가계부가 존재하지 않습니다.")
        );

        accountRepository.deleteById(id);
        return new ResponseEntity<>(new StatusResponseDto("가계부 삭제 성공"), HttpStatus.OK);

    }

    @Transactional(readOnly = true)
    public ResponseEntity<AccountTotalListResponseDto> getTotalStatus(Long accountId, AccountSearchCondition condition, String username) {
        User user = userCheck(username);
        List<AccountTotalResponseDto> totalStatus = ledgerHistoryRepository.getTotalStatus(accountId, condition);

        Long expenditure_sum = 0L;
        Long income_sum = 0L;

        for (AccountTotalResponseDto status : totalStatus) {
            expenditure_sum += Optional.ofNullable(status.getExpenditure_sum()).orElse(0L);
            income_sum += Optional.ofNullable(status.getIncome_sum()).orElse(0L);
        }

        AccountTotalListResponseDto accountTotalListResponseDto = AccountTotalListResponseDto.builder()
                .accountTotalResponseDtoList(totalStatus)
                .expenditure_sum(expenditure_sum)
                .income_sum(income_sum)
                .build();
        return new ResponseEntity<>(accountTotalListResponseDto, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public Page<LedgerHistoryResponseDto> getStatus(Long accountId, AccountSearchCondition condition, String username, Pageable pageable) {
        User user = userCheck(username);
        return ledgerHistoryRepository.getStatus(accountId, condition, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MonthSumResponseDto> getStatistics(Long accountId, AccountSearchCondition condition, String username, Pageable pageable) {
        User user = userCheck(username);
        return ledgerHistoryRepository.getStatistic(accountId, condition, pageable);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<MonthSumResponseDto>> getDifference(Long accountId, AccountSearchCondition condition, String username) {
        User user = userCheck(username);

            String dateType = condition.getDateType();

            LocalDate currentDate = LocalDate.parse(condition.getDate()+"-01");
            LocalDate pastDate = LocalDate.parse(condition.getDate()+"-01");

            int quarter = 0;

            if (dateType.equals("month")) {
                pastDate = currentDate.minusMonths(1);
            } else if (dateType.equals("year")) {
                pastDate = currentDate.minusYears(1);
            } else if (dateType.equals("quarter")){
                quarter = (int) Math.ceil(currentDate.getMonthValue() / 3.0);
                pastDate = currentDate.minusYears(1);
            }

        List<MonthSumResponseDto> difference = new ArrayList<>();
        difference.add(ledgerHistoryRepository.getDifference(accountId, condition, currentDate, quarter));
        difference.add(ledgerHistoryRepository.getDifference(accountId, condition, pastDate, quarter));

        return new ResponseEntity<>(difference, HttpStatus.OK);
    }

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

    private Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 가계부가 존재하지 않습니다.")
        );
    }
}