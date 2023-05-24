package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
<<<<<<< HEAD
import com.example.apoorpoor_backend.repository.BeggarRepository;
=======
import com.example.apoorpoor_backend.entity.Beggar;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import com.example.apoorpoor_backend.user.User;
import com.example.apoorpoor_backend.user.repository.UserRepository;
>>>>>>> beggars
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BeggarService {

    private final BeggarRepository beggarRepository;
    private final UserRepository userRepository;

    public StatusResponseDto createBeggar(BeggarRequestDto beggarRequestDto, String memberId) {
        Optional<User> findUser = userRepository.findByMemberId(memberId);
        if(findUser.isEmpty())
            throw new IllegalStateException("거지가 되지 못했어요..");
        beggarRepository.save(new Beggar(beggarRequestDto, findUser.get()));
        return new StatusResponseDto("거지가 되었어요...");
    }

    public BeggarResponseDto updateBeggar(BeggarRequestDto beggarRequestDto, String memberId) {
        Optional<User> findUser = userRepository.findByMemberId(memberId);
        if(findUser.isEmpty())
            throw new IllegalStateException("거지를 찾을 수 없습니다.");

        Beggar beggar = beggarRepository.findByUserId(findUser.get().getId());
        beggar.update(beggarRequestDto);
        return new BeggarResponseDto(beggar);
    }

    public BeggarResponseDto findBeggar(String memberId) {
        Optional<User> findUser = userRepository.findByMemberId(memberId);
        if(findUser.isEmpty())
            throw new IllegalStateException("거지 조회 실패");
        Beggar beggar = beggarRepository.findByUserId(findUser.get().getId());
        return new BeggarResponseDto(beggar);
    }

}
