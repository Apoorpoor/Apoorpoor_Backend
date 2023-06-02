package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.BeggarRequestDto;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.UserRoleEnum;
import com.example.apoorpoor_backend.model.enumType.ExpType;
import com.example.apoorpoor_backend.repository.BeggarRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BeggarServiceTest {
    @Autowired
    private BeggarRepository beggarRepository;

    @Test
    void createBeggar() {
    }

    @Test
    void findBeggar() {
    }

    @Test
    void updateBeggar() {
    }

    @Test
    void userCheck() {
    }

    @Test
    void beggarCheck() {
    }

    @Test
    @DisplayName("beggar 경험치 획득 test")
    void beggarUpdateExpTest() {
        //given
        User user = User.builder()
                .username("kakao21232212258")
                .kakaoId(21232212258L)
                .role(UserRoleEnum.USER)
                .build();

        String nickname = "TestBeggar";
        BeggarRequestDto beggarRequestDto = BeggarRequestDto.builder()
                .nickname(nickname)
                .build();

        Beggar beggar = new Beggar(beggarRequestDto, user);
        beggarRepository.save(beggar);

        Long exp = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();

        //when
        beggar.updateExp(BeggarExpUpResponseDto.builder()
                        .exp(exp)
                        //.level(level)
                        //.point(point)
                        .build());

        //then
        assertEquals(100L, exp);
        assertEquals(100L, beggar.getExp());


    }

    @Test
    @DisplayName("beggar 경험치 두번 획득 test")
    void beggarUpdateExpTest2() {
        //given
        User user = User.builder()
                .username("kakao21232212258")
                .kakaoId(21232212258L)
                .role(UserRoleEnum.USER)
                .build();

        String nickname = "TestBeggar";
        BeggarRequestDto beggarRequestDto = BeggarRequestDto.builder()
                .nickname(nickname)
                .build();

        Beggar beggar = new Beggar(beggarRequestDto, user);
        beggarRepository.save(beggar);

        Long exp = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();

        //when
        beggar.updateExp(BeggarExpUpResponseDto.builder()
                .exp(exp)
                //.level(level)
                //.point(point)
                .build());

        Long exp2 = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();

        beggar.updateExp(BeggarExpUpResponseDto.builder()
                .exp(exp2)
                //.level(level)
                //.point(point)
                .build());

        //then
        assertEquals(100L, exp);
        assertEquals(200L, beggar.getExp());

    }
}