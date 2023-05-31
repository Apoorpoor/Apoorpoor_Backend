package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class BeggarService {

    private final BeggarRepository beggarRepository;
    private final UserRepository userRepository;

    public ResponseEntity<StatusResponseDto> createBeggar(BeggarRequestDto beggarRequestDto, String username) {
        User findUser = userCheck(username);
        beggarRepository.save(new Beggar(beggarRequestDto, findUser));
        return new ResponseEntity<>(new StatusResponseDto("거지가 되었어요..."), HttpStatus.OK );
    }

    public ResponseEntity<BeggarResponseDto> findBeggar(String username) {
        User findUser = userCheck(username);
        Beggar beggar = beggarCheck(username);
        return new ResponseEntity<>(BeggarResponseDto.of(beggar), HttpStatus.OK);
    }

    public ResponseEntity<BeggarResponseDto> updateBeggar(BeggarRequestDto beggarRequestDto, String username) {
        User findUser = userCheck(username);
        Beggar beggar = beggarCheck(username);
        beggar.update(beggarRequestDto);
        return new ResponseEntity<>(BeggarResponseDto.of(beggar), HttpStatus.OK);
    }

    public User userCheck(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저 입니다.")
        );
    }

    public Beggar beggarCheck(String username) {
        return beggarRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("거지를 찾을 수 없습니다.")
        );
    }

}
