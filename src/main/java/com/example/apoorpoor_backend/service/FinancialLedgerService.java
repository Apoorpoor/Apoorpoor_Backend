package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.auth.PrincipalDetails;
import com.example.apoorpoor_backend.dto.LedgerListResponseDto;
import com.example.apoorpoor_backend.dto.LedgerRequestDto;
import com.example.apoorpoor_backend.dto.LedgerResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.model.AccountType;
import com.example.apoorpoor_backend.model.Balance;
import com.example.apoorpoor_backend.model.FinancialLedger;
import com.example.apoorpoor_backend.repository.BalanceRepository;
import com.example.apoorpoor_backend.repository.FinancialLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class FinancialLedgerService {

    private final FinancialLedgerRepository financialLedgerRepository;
    private final BalanceRepository balanceRepository;

    public ResponseEntity<LedgerResponseDto> createLedger(LedgerRequestDto ledgerRequestDto, PrincipalDetails principalDetails) {
        String username = principalDetails.getUsername();
        String ledgerTitle = ledgerRequestDto.getLedgerTitle();
        String incomeType = ledgerRequestDto.getIncomeType();
        String expenditureType = ledgerRequestDto.getExpenditureType();
        Long income = ledgerRequestDto.getIncome();
        Long expenditure = ledgerRequestDto.getExpenditure();
        AccountType accountType = ledgerRequestDto.getAccountType();

        LedgerResponseDto ledgerResponseDto = null;

        if(accountType.equals(AccountType.INCOME)) {
            ledgerResponseDto = LedgerResponseDto.builder()
                    .username(username)
                    .ledgerTitle(ledgerTitle)
                    .incomeType(incomeType)
                    .expenditureType(null)
                    .income(income)
                    .expenditure(0L)
                    .accountType(accountType)
                    .build();
        } else {
            ledgerResponseDto = LedgerResponseDto.builder()
                    .username(username)
                    .ledgerTitle(ledgerTitle)
                    .incomeType(null)
                    .expenditureType(expenditureType)
                    .income(0L)
                    .expenditure(expenditure)
                    .accountType(accountType)
                    .build();
        }

        FinancialLedger financialLedger = new FinancialLedger(ledgerResponseDto);
        financialLedgerRepository.save(financialLedger);
        Optional<Balance> findBalance = balanceRepository.findByLedgerTitle(ledgerTitle);

        if(findBalance.isPresent()) {
            Long incomeTotal = ledgerRequestDto.getIncome() + findBalance.get().getIncomeTotal();
            Long expenditureTotal = ledgerRequestDto.getExpenditure() + findBalance.get().getExpenditureTotal();

            findBalance.get().update(incomeTotal, expenditureTotal);
        }else{
            Balance balance = new Balance(ledgerRequestDto.getIncome(), ledgerRequestDto.getExpenditure(), ledgerRequestDto.getLedgerTitle());
            balanceRepository.save(balance);
        }


        return new ResponseEntity<>(ledgerResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<LedgerListResponseDto> getAllLedger(String ledgerTitle, PrincipalDetails principalDetails) {
        String username = principalDetails.getUsername();
        List<FinancialLedger> financialLedgerList = financialLedgerRepository.findAllByUsernameAndLedgerTitleOrderByCreatedAt(username, ledgerTitle);

        List<LedgerResponseDto> ledgerResponseDtoList = new ArrayList<>();
        LedgerResponseDto ledgerResponseDto;
        for (FinancialLedger financialLedger : financialLedgerList) {
            ledgerResponseDto = LedgerResponseDto.builder()
                    .username(username)
                    .ledgerTitle(ledgerTitle)
                    .accountType(financialLedger.getAccountType())
                    .incomeType(financialLedger.getIncomeType())
                    .income(financialLedger.getIncome())
                    .expenditure(financialLedger.getExpenditure())
                    .expenditureType(financialLedger.getExpenditureType())
                    .build();
            ledgerResponseDtoList.add(ledgerResponseDto);
        }

        Optional<Balance> balance = balanceRepository.findByLedgerTitle(ledgerTitle);

        if(balance.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 balance 입니다.");
        } else {
            LedgerListResponseDto ledgerListResponseDto = new LedgerListResponseDto(ledgerResponseDtoList, balance.get());
            return new ResponseEntity<>(ledgerListResponseDto, HttpStatus.OK);
        }

    }

    public ResponseEntity<LedgerResponseDto> getLedger(String ledgerTitle, PrincipalDetails principalDetails){
        String username = principalDetails.getUsername();
        Optional<FinancialLedger> financialLedger = financialLedgerRepository.findByUsernameAndLedgerTitle(username, ledgerTitle);

        if(financialLedger.isEmpty()){
            throw new IllegalArgumentException("해당 가계부가 존재하지 않습니다.");
        }
        LedgerResponseDto ledgerResponseDto = LedgerResponseDto.builder()
                .username(username)
                .ledgerTitle(financialLedger.get().getLedgerTitle())
                .incomeType(financialLedger.get().getIncomeType())
                .expenditureType(financialLedger.get().getExpenditureType())
                .income(financialLedger.get().getIncome())
                .expenditure(financialLedger.get().getExpenditure())
                .accountType(financialLedger.get().getAccountType())
                .build();

        return new ResponseEntity<>(ledgerResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<LedgerResponseDto> updateLedger(String ledgerTitle, LedgerRequestDto ledgerRequestDto, PrincipalDetails principalDetails){
        String username = principalDetails.getUsername();
        Optional<FinancialLedger> financialLedger = financialLedgerRepository.findByUsernameAndLedgerTitle(username, ledgerTitle);

        if(financialLedger.isEmpty()){
            throw new IllegalArgumentException("해당 가계부가 존재하지 않습니다.");
        }

        String incomeType = ledgerRequestDto.getIncomeType();
        String expenditureType = ledgerRequestDto.getExpenditureType();
        Long income = ledgerRequestDto.getIncome();
        Long expenditure = ledgerRequestDto.getExpenditure();
        AccountType accountType = ledgerRequestDto.getAccountType();

        LedgerResponseDto ledgerResponseDto = null;

        if(accountType.equals(AccountType.INCOME)) {
            ledgerResponseDto = LedgerResponseDto.builder()
                    .username(username)
                    .ledgerTitle(ledgerTitle)
                    .incomeType(incomeType)
                    .expenditureType(null)
                    .income(income)
                    .expenditure(0L)
                    .accountType(accountType)
                    .build();
        } else {
            ledgerResponseDto = LedgerResponseDto.builder()
                    .username(username)
                    .ledgerTitle(ledgerTitle)
                    .incomeType(null)
                    .expenditureType(expenditureType)
                    .income(0L)
                    .expenditure(expenditure)
                    .accountType(accountType)
                    .build();
        }
        financialLedger.get().update(ledgerResponseDto);
        return new ResponseEntity<>(ledgerResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<StatusResponseDto> deleteLedger(String ledgerTitle, PrincipalDetails principalDetails){
        String username = principalDetails.getUsername();
        Optional<FinancialLedger> financialLedger = financialLedgerRepository.findByUsernameAndLedgerTitle(username, ledgerTitle);
        if(financialLedger.isEmpty()){
            throw new IllegalArgumentException("해당 가계부가 존재하지 않습니다.");
        }
        financialLedgerRepository.deleteAllByUsernameAndLedgerTitle(username, ledgerTitle);
        return new ResponseEntity<>(new StatusResponseDto("가계부 삭제 성공"), HttpStatus.OK);
    }

}