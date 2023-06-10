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
    private final Random random = new Random();

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
        ExpType expType = ExpType.FILL_LEDGER;
        /////////////////////////////////////////////////////////////////////////
        if(expenditureType.equals(ExpenditureType.SAVINGS)) {
            expType = ExpType.BEST_SAVER;
        }
        ////////////////////////////////////////////////////////////////////////

        // 1개 등록시 point +10, exp +10 (누적), 레벨업 확인
        beggarService.updateExpNew(user.getUsername(), expType);  /////////////////////////////////////////


        // 지출/수입 구분에 따라 랜덤 멘트 프론트에 전달하기
        String randomMENT = getMent(accountType);
        return new ResponseEntity<>(new StatusResponseDto(randomMENT), HttpStatus.OK);

    }

    public String getMent(AccountType accountType) {
       MentType mentType;
        if (accountType == AccountType.EXPENDITURE) {
            List<MentType> expenditureMentTypes = Arrays.asList(MentType.MENT1, MentType.MENT2, MentType.MENT3, MentType.MENT4, MentType.MENT5, MentType.MENT6,MentType.MENT7, MentType.MENT8, MentType.MENT9, MentType.MENT10, MentType.MENT11);
            mentType = expenditureMentTypes.get(random.nextInt(expenditureMentTypes.size()));
        } else {
            List<MentType> incompeMentTypes = Arrays.asList(MentType.MENT12, MentType.MENT13, MentType.MENT14, MentType.MENT15, MentType.MENT16, MentType.MENT17, MentType.MENT18);
            mentType = incompeMentTypes.get(random.nextInt(incompeMentTypes.size()));
        }
        List<String> ments = mentType.getMents();
        String randomMENT = ments.get(random.nextInt(ments.size()));
        return randomMENT;
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

        LocalDate localDate = LocalDate.parse(requestDto.getDate());
        PaymentMethod paymentMethod = requestDto.getPaymentMethod();

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

        LedgerHistory ledgerHistory = ledgerHistoryCheck(id);

        Optional<Balance> findBalance = getBalance(ledgerHistory.getAccount());

        if(findBalance.isPresent()) {
            Long incomeTotal = findBalance.get().getIncomeTotal()+ledgerHistory.getIncome();
            Long expenditureTotal = findBalance.get().getExpenditureTotal()+ledgerHistory.getExpenditure();

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
        Optional<Balance> findBalance = balanceRepository.findByAccountId(account.getId());
        return findBalance;
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("푸어를 찾을 수 없습니다.")
        );
    }


}
