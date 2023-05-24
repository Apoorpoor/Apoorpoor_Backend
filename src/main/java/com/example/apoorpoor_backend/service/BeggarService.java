package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.BeggarResponseDto;
import com.example.apoorpoor_backend.dto.StatusResponseDto;
import com.example.apoorpoor_backend.repository.BeggarRepository;
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

    public StatusResponseDto createBeggar(BeggarRequestDto beggarRequestDto, String email) {

        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isEmpty())
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");

        beggarRepository.save(new Beggar(beggarRequestDto, findUser.get()));
        return new StatusResponseDto("거지가 되었어요...");
    }

    @Transactional(readOnly = true)
    public BeggarResponseDto findBeggar(String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isEmpty())
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");

        Optional<Beggar> findBeggar = beggarRepository.findByUserId(findUser.get().getId());

        return new BeggarResponseDto(findBeggar.get());
    }

    public BeggarResponseDto updateBeggar(BeggarRequestDto beggarRequestDto, String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        if(findUser.isEmpty())
            throw new IllegalStateException("사용자를 찾을 수 없습니다.");

        Beggar beggar = new Beggar();

        beggar = beggarRepository.findByUserId(findUser.get().getId()).orElseThrow(
                () -> new IllegalStateException("거지를 찾을 수 없습니다.")
        );

        beggar.update(beggarRequestDto);
        return new BeggarResponseDto(beggar);
    }
}
