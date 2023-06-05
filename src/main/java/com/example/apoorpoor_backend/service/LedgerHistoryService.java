package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.model.*;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import com.example.apoorpoor_backend.repository.*;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

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

    public ResponseEntity<StatusResponseDto> createLedgerHistory(LedgerHistoryRequestDto requestDto, String username) {
        User user = userCheck(username);
        Account account = accountCheck(requestDto.getAccountId());

        String title = requestDto.getTitle();
        AccountType accountType = requestDto.getAccountType();
        IncomeType incomeType = requestDto.getIncomeType();
        ExpenditureType expenditureType = requestDto.getExpenditureType();
        PaymentMethod paymentMethod = requestDto.getPaymentMethod();
        Long income = requestDto.getIncome();
        Long expenditure = requestDto.getExpenditure();
        LocalDate localDate = LocalDate.parse(requestDto.getDateTime());

        if(accountType == AccountType.INCOME){
            expenditureType = null;
            paymentMethod = null;
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

        LedgerHistory ledgerHistory = new LedgerHistory(account, title, accountType, incomeType, expenditureType, paymentMethod, income, expenditure, localDate);

        ledgerHistoryRepository.save(ledgerHistory);

        Optional<Balance> findBalance = getBalance(account);

        if(findBalance.isPresent()) {
            Long incomeTotal = income + findBalance.get().getIncomeTotal();
            Long expenditureTotal = expenditure + findBalance.get().getExpenditureTotal();

            findBalance.get().update(incomeTotal, expenditureTotal);
        }else{
            Balance balance = new Balance(income, expenditure, account);
            balanceRepository.save(balance);
        }

        // 1개 등록시 point +10, exp +10 (누적), 레벨업 확인
        beggarService.updateExpNew(user.getUsername(), 10L);

        return new ResponseEntity<>(new StatusResponseDto("거래 내역 저장 성공"), HttpStatus.OK);

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

        LocalDate localDate = LocalDate.parse(requestDto.getDateTime());
        PaymentMethod paymentMethod = requestDto.getPaymentMethod();

        if(accountType == AccountType.INCOME){
            expenditureType = null;
            paymentMethod = null;
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
            Long incomeTotal = income + findBalance.get().getIncomeTotal()-ledgerHistory.getIncome();;
            Long expenditureTotal = expenditure + findBalance.get().getExpenditureTotal()-ledgerHistory.getExpenditure();

            findBalance.get().update(incomeTotal, expenditureTotal);
        }else{
            Balance balance = new Balance(income, expenditure, account);
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

        return new ResponseEntity<>(LedgerHistoryResponseDto.of(ledgerHistory), HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDto> deleteLedgerHistory(Long id, String username){
        User user = userCheck(username);
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
        Optional<Balance> findBalance = balanceRepository.findByAccountId(account.getId());
        return findBalance;
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }


}
