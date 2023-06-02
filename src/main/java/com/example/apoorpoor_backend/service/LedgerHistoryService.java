package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.LedgerHistoryRequestDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.Balance;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.example.apoorpoor_backend.model.enumType.PaymentMethod;
import com.example.apoorpoor_backend.repository.AccountRepository;
import com.example.apoorpoor_backend.repository.BalanceRepository;
import com.example.apoorpoor_backend.repository.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //LocalDate localDate = LocalDate.parse(requestDto.getDatetime()); // 2022-10-23
        String localDate = requestDto.getDatetime();

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

        Optional<Balance> findBalance = balanceRepository.findByAccountId(account.getId());

        if(findBalance.isPresent()) {
            Long incomeTotal = income + findBalance.get().getIncomeTotal();
            Long expenditureTotal = expenditure + findBalance.get().getExpenditureTotal();

            findBalance.get().update(incomeTotal, expenditureTotal);
        }else{
            Balance balance = new Balance(income, expenditure, account);
            balanceRepository.save(balance);
        }

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
        String localDate = requestDto.getDatetime();
        // LocalDate localDate = LocalDate.parse(requestDto.getDatetime());
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
        Optional<LedgerHistory> ledgerHistory = ledgerHistoryRepository.findById(id);
        LedgerHistoryResponseDto responseDto = LedgerHistoryResponseDto.builder()
                .title(title)
                .accountType(accountType)
                .incomeType(incomeType)
                .expenditureType(expenditureType)
                .paymentMethod(paymentMethod)
                .income(income)
                .expenditure(expenditure)
                .date(localDate)
                .build();
        ledgerHistory.get().update(responseDto);

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


}
