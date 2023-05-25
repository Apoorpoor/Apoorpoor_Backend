package com.example.apoorpoor_backend.user.service;

import com.example.apoorpoor_backend.dto.UserSignUpDto;
import com.example.apoorpoor_backend.entity.Beggar;
import com.example.apoorpoor_backend.entity.Role;
import com.example.apoorpoor_backend.entity.User;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import com.example.apoorpoor_backend.repository.UserRepository;
import com.example.apoorpoor_backend.user.dto.UserLogOutDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BeggarRepository beggarRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignUpDto userSignUpDto) throws Exception {

        if (userRepository.findByMemberId(userSignUpDto.getMemberId()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .memberId(userSignUpDto.getMemberId())
                .nickname(userSignUpDto.getNickname())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    public void logOut(UserLogOutDto userLogOutDto) {
        String memberId = userLogOutDto.getMemberId();
        User findUser = userCheck(memberId);
        findUser.updateRefreshToken(null);
        userRepository.save(findUser);
    }

    private User userCheck(String memberId) {
        return userRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }

    public String getBeggarName(String memberId) {
        Optional<User> findUser = userRepository.findByMemberId(memberId);
        if(findUser.isEmpty())
            throw new IllegalStateException("거지 조회 실패");
        Beggar beggar = beggarRepository.findByUserId(findUser.get().getId());
        return beggar.getNickname();
    }
}


