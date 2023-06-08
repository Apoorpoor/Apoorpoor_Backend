//package com.example.apoorpoor_backend.service;
//
//import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
//import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
//import com.example.apoorpoor_backend.model.Badge;
//import com.example.apoorpoor_backend.model.Beggar;
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
//import com.example.apoorpoor_backend.model.enumType.ExpType;
//import com.example.apoorpoor_backend.model.enumType.LevelType;
//import com.example.apoorpoor_backend.repository.badge.BadgeRepository;
//import com.example.apoorpoor_backend.repository.beggar.BeggarRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Transactional
//class BeggarServiceTest {
//    @Autowired
//    private BeggarRepository beggarRepository;
//
//    @Autowired
//    private BadgeRepository badgeRepository;
//
//    @BeforeEach
//    void beforeEach() {
//        beggarRepository.deleteAll();
//    }
//
//    @Test
//    void createBeggar() {
//    }
//
//    @Test
//    void findBeggar() {
//    }
//
//    @Test
//    void updateBeggar() {
//    }
//
//    @Test
//    void userCheck() {
//    }
//
//    @Test
//    void beggarCheck() {
//    }
//
//    @Test
//    @DisplayName("레벨로 경험치 가져오기 test")
//    void getExpCheckTest() {
//        //given
//        Long level = 2L;
//
//        //when
//        Long nextExp = LevelType.getNextExpByLevel(level);
//
//        //then
//        assertEquals(nextExp, LevelType.LV2.getNextExp());
//    }
//
//    @Test
//    @DisplayName("beggar 경험치 획득 test")
//    void beggarUpdateExpTest() {
//        //given
//        User user = User.builder()
//                .username("kakao21232212258")
//                .kakaoId(21232212258L)
//                .role(UserRoleEnum.USER)
//                .build();
//
//        String nickname = "TestBeggar";
//        BeggarRequestDto beggarRequestDto = BeggarRequestDto.builder()
//                .nickname(nickname)
//                .build();
//
//        Beggar beggar = new Beggar(beggarRequestDto, user);
//        beggarRepository.save(beggar);
//
//        Long exp = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();
//        Long level = beggar.getLevel();
//        Long point = beggar.getPoint() + ExpType.FILL_LEDGER.getAmount();
//
//        if (LevelType.getNextExpByLevel(level) <= exp) {
//            level ++;
//        }
//
//        //when
//        beggar.updateExp(BeggarExpUpResponseDto.builder()
//                        .exp(exp)
//                        .level(level)
//                        .point(point)
//                        .build());
//
//        //then
//        assertEquals(10L, exp);
//        assertEquals(10L, beggar.getExp());
//        assertEquals(1L, beggar.getLevel());
//        assertEquals(10L, beggar.getPoint());
//
//    }
//
//    @Test
//    @DisplayName("beggar 경험치 두번 획득 test")
//    void beggarUpdateExpTest2() {
//        //given
//        User user = User.builder()
//                .username("kakao21232212258")
//                .kakaoId(21232212258L)
//                .role(UserRoleEnum.USER)
//                .build();
//
//        String nickname = "TestBeggar";
//        BeggarRequestDto beggarRequestDto = BeggarRequestDto.builder()
//                .nickname(nickname)
//                .build();
//
//        Beggar beggar = new Beggar(beggarRequestDto, user);
//        beggarRepository.save(beggar);
//
//        Long exp = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();
//        Long level = beggar.getLevel();
//        Long point = beggar.getPoint() + ExpType.FILL_LEDGER.getAmount();
//
//        if (LevelType.getNextExpByLevel(level) <= exp) {
//            level ++;
//        }
//
//        //when
//        beggar.updateExp(BeggarExpUpResponseDto.builder()
//                .exp(exp)
//                .level(level)
//                .point(point)
//                .build());
//
//        Long exp2 = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();
//        Long point2 = beggar.getPoint() + ExpType.FILL_LEDGER.getAmount();
//
//        beggar.updateExp(BeggarExpUpResponseDto.builder()
//                .exp(exp2)
//                .level(level)
//                .point(point2)
//                .build());
//
//        //then
//        assertEquals(10L, exp);
//        assertEquals(20L, beggar.getExp());
//        assertEquals(20L, beggar.getPoint());
//        assertEquals(1L, beggar.getLevel());
//
//    }
//
//    @Test
//    @DisplayName("beggar 레벨업 test")
//    void beggarUpdateLevelUpTest2() {
//        //given
//        User user = User.builder()
//                .username("kakao21232212258")
//                .kakaoId(21232212258L)
//                .role(UserRoleEnum.USER)
//                .build();
//
//        String nickname = "TestBeggar";
//        BeggarRequestDto beggarRequestDto = BeggarRequestDto.builder()
//                .nickname(nickname)
//                .build();
//
//        Beggar beggar = new Beggar(beggarRequestDto, user);
//        beggarRepository.save(beggar);
//
//        Long exp = 100L;
//        Long level = beggar.getLevel();
//
//
//
//        //when
//        if (LevelType.getNextExpByLevel(level) <= exp) {
//            level ++;
//        }
//
//        beggar.updateExp(BeggarExpUpResponseDto.builder()
//                .exp(exp)
//                .level(level)
//                //.point(point)
//                .build());
//
//        //then
//        assertEquals(100L, beggar.getExp());
//        assertEquals(2L, beggar.getLevel());
//
//    }
//
//    @Test
//    @DisplayName("Badge 저장 N대 M  테스트")
//    void saveBade() {
//        //given
//        Badge badge = new Badge(1L, "티끌 모아 태산");
//        badgeRepository.save(badge);
//
//        //beggar객체 1 생성
//        User user1 = User.builder()
//                .username("kakao21232212258")
//                .kakaoId(21232212258L)
//                .role(UserRoleEnum.USER)
//                .build();
//
//        String nickname1 = "TestBeggar1";
//        BeggarRequestDto beggarRequestDto1 = BeggarRequestDto.builder()
//                .nickname(nickname1)
//                .build();
//
//        Beggar beggar1 = new Beggar(beggarRequestDto1, user1);
//        beggarRepository.save(beggar1);
//
//        //beggar객체2 생성
//        User user2 = User.builder()
//                .username("kakao21232212254")
//                .kakaoId(21232212254L)
//                .role(UserRoleEnum.USER)
//                .build();
//
//        String nickname2 = "TestBeggar2";
//        BeggarRequestDto beggarRequestDto2 = BeggarRequestDto.builder()
//                .nickname(nickname2)
//                .build();
//
//        Beggar beggar2 = new Beggar(beggarRequestDto2, user2);
//        beggarRepository.save(beggar2);
//
//    }
//}