package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.controller.AccountController;
import com.example.apoorpoor_backend.dto.AccountRequestDto;
import com.example.apoorpoor_backend.dto.AccountResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.model.Account;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.repository.AccountRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public ResponseEntity<StatusResponseDto> createAccount(AccountRequestDto requestDto, String username) {
        User user = userCheck(username);
        accountRepository.save(new Account(requestDto, user));
        return new ResponseEntity<>(new StatusResponseDto("가계부 생성 완료"), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(String username){
        User user = userCheck(username);
        List<Account>accountList = accountRepository.findByAllOrderByCreatedDesc();
        List<AccountResponseDto> accountResponseDtoList = accountList
                .stream()
                .map(AccountResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accountResponseDtoList);
    }



    @Transactional(readOnly = true)
    public ResponseEntity<AccountResponseDto> getAccount(Long id, String username) {
        User user = userCheck(username);
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 가계부가 존재하지 않습니다.")
        );

        AccountResponseDto accountResponseDto = AccountResponseDto.builder()
                .id(account.getId())
                .title(account.getTitle())
                .userId(account.getUser().getId())
                .ledgerHistories(account.getLedgerHistories())
                .build();
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
