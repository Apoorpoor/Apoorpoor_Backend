package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.user.UserResponseDto;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.ledgerhistory.LedgerHistoryRepository;
import com.example.apoorpoor_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LedgerHistoryRepository ledgerHistoryRepository;

    public ResponseEntity<Long> setAge(Long age, String username) {
        User finduser = userCheck(username);
        finduser.updateAge(age);
        return new ResponseEntity<>(age, HttpStatus.OK);
    }

    public ResponseEntity<String> setGender(String gender, String username){
        User findUser = userCheck(username);
        findUser.updateGender(gender);
        return new ResponseEntity<>(gender, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<UserResponseDto> userInfo(String username) {
        User findUser = userCheck(username);

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(findUser.getId())
                .username(findUser.getUsername())
                .age(findUser.getAge())
                .gender(findUser.getGender())
                .build();
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<TotalSumResponseDto>> getStatus(String username) {
        User findUser = userCheck(username);
        List<TotalSumResponseDto> mypageStatus = ledgerHistoryRepository.getMypageStatus(findUser.getId());
        return new ResponseEntity<>(mypageStatus, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<RecentSumResponseDto> getRecentStatus(String username) {
        User findUser = userCheck(username);
        List<ExpenditureSumResponseDto> expenditureRecentStatus = ledgerHistoryRepository.getExpenditureRecentStatus(findUser.getId());
        List<IncomeSumResponseDto> incomeRecentStatus = ledgerHistoryRepository.getIncomeRecentStatus(findUser.getId());

        RecentSumResponseDto recentStatus = RecentSumResponseDto.builder()
                .expenditureSum(expenditureRecentStatus)
                .incomeSum(incomeRecentStatus)
                .build();

        return new ResponseEntity<>(recentStatus, HttpStatus.OK);
    }

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

    @Transactional(readOnly = true)
    public List<User> getUserList() {
        return userRepository.findAll();
    }
}
