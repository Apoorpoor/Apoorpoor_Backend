package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.AccountRequestDto;
import com.example.apoorpoor_backend.dto.AccountResponseDto;
import com.example.apoorpoor_backend.dto.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.Balance;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.AccountRepository;
import com.example.apoorpoor_backend.repository.BalanceRepository;
import com.example.apoorpoor_backend.repository.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        accountRepository.save(new Account(requestDto, user));
        return new ResponseEntity<>(new StatusResponseDto("가계부 생성 완료"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(String username){
        User user = userCheck(username);
        List<Account> accountList = accountRepository.findAllByOrderByCreatedAtDesc();

        List<AccountResponseDto> accountResponseDtoList = new ArrayList<>();

        for (Account account : accountList) {
            List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList = new ArrayList<>();

            for (LedgerHistory ledgerHistory : account.getLedgerHistories()) {
                ledgerHistoryResponseDtoList.add(LedgerHistoryResponseDto.of(ledgerHistory));
            }
            accountResponseDtoList.add(new AccountResponseDto(account, ledgerHistoryResponseDtoList));
        }

        return ResponseEntity.ok(accountResponseDtoList);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<AccountResponseDto> getAccount(Long id, String username) {
        User user = userCheck(username);
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 가계부가 존재하지 않습니다.")
        );

        Optional<Balance> findBalance = balanceRepository.findByAccountId(id);

        Balance balance = null;

        if(findBalance.isPresent()) balance = findBalance.get();

        List<LedgerHistory> ledgerHistoryList = account.getLedgerHistories();

        List<LedgerHistoryResponseDto> ledgerHistoryResponseDtoList = new ArrayList<>();

        for (LedgerHistory ledgerHistory : ledgerHistoryList) {
            ledgerHistoryResponseDtoList.add(LedgerHistoryResponseDto.of(ledgerHistory));
        }

        AccountResponseDto accountResponseDto = new AccountResponseDto(account.getId(), account.getTitle(),
                account.getUser().getId(), ledgerHistoryResponseDtoList, balance);
        return new ResponseEntity<>(accountResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<AccountResponseDto> updateAccount(Long id, AccountRequestDto requestDto, String username) {
        User user = userCheck(username);
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 가계부가 존재하지 않습니다.")
        );
        account.update(requestDto);
        AccountResponseDto accountResponseDto = new AccountResponseDto(account);
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

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }


}
