//package com.example.apoorpoor_backend.service;
//
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.UserRoleEnum;
//import com.example.apoorpoor_backend.repository.UserRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @AfterEach
//    void tearDown() {
//        userRepository.deleteAllInBatch();
//    }
//
//    @DisplayName("나이를 설정한다.")
//    @Test
//    void setAge() throws Exception {
//        //given
//        User user = createUser("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);
//
//        //when
//        user.updateGender("female");
//
//        //then
//        assertThat(user.getGender()).isEqualTo("female");
//    }
//
//    @DisplayName("성별을 설정한다.")
//    @Test
//    void setGender() throws Exception {
//        //given
//        User user = createUser("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);
//
//        //when
//        user.updateGender("female");
//
//        //then
//        assertThat(user.getGender()).isEqualTo("female");
//    }
//
//    @DisplayName("유저 정보를 조회한다.")
//    @Test
//    void userInfo() throws Exception {
//        //given
//        User user = createUser("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);
//        userRepository.save(user);
//
//        //when
//        Optional<User> findUser = userRepository.findByUsername(user.getUsername());
//
//        //then
//        assertThat(findUser.get())
//                .extracting("username", "password", "role", "kakaoId")
//                .containsExactly("kakao111222", "yumin4970", UserRoleEnum.USER, 121212L);
//
//    }
//
//    private User createUser(String username, String password, UserRoleEnum role, Long kakaoId){
//        return User.builder()
//                .username(username)
//                .password(password)
//                .role(role)
//                .kakaoId(kakaoId)
//                .build();
//    }
//
//}