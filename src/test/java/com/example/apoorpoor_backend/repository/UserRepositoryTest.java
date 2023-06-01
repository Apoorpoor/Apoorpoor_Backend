package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @DisplayName("유저네임으로 유저를 조회한다.")
    @Test
    void findByUsername() throws Exception {
        //given
        User user = createUser("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);
        userRepository.save(user);

        //when
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());

        //then
        assertThat(findUser.get())
                .extracting("username", "password", "role", "kakaoId")
                .containsExactly("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);


    }

    @DisplayName("카카오 아이디로 유저를 조회한다.")
    @Test
    void findByKakaoId() throws Exception {
        //given
        User user = createUser("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);
        userRepository.save(user);

        //when
        Optional<User> findUser = userRepository.findByKakaoId(user.getKakaoId());

        //then
        assertThat(findUser.get())
                .extracting("username", "password", "role", "kakaoId")
                .containsExactly("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);

    }

    private User createUser(String username, String password, UserRoleEnum role, Long kakaoId){
        return User.builder()
                .username(username)
                .password(password)
                .role(role)
                .kakaoId(kakaoId)
                .build();
    }
}