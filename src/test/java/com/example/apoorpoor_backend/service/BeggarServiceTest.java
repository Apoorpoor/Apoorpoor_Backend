package com.example.apoorpoor_backend.service;

import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
import com.example.apoorpoor_backend.dto.chat.BadWordFiltering;
import com.example.apoorpoor_backend.model.Beggar;
import com.example.apoorpoor_backend.model.User;
import com.example.apoorpoor_backend.model.enumType.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BeggarServiceTest {

    @Test
    void createBeggar() {
        //given
        User user = new User("username", null, UserRoleEnum.USER, 2222222L, "kakaoName");
        String nickname = "beggarTest";
        BeggarRequestDto beggarRequestDto = new BeggarRequestDto(nickname);

        //when
        Beggar beggar = Beggar.builder()
                .nickname(beggarRequestDto.getNickname())
                .user(user)
                .point(0L)
                .level(1L)
                .exp(0L)
                .build();

        //then
        assertEquals(user, beggar.getUser());
        assertEquals(nickname, beggar.getNickname());
        assertEquals(0L, beggar.getExp());
        assertEquals(0L, beggar.getPoint());
        assertEquals(1L, beggar.getLevel());
        assertNull(beggar.getTop());
        assertNull(beggar.getBottom());
        assertNull(beggar.getShoes());
        assertNull(beggar.getAcc());
        assertNull(beggar.getCustom());
        assertNull(beggar.getChallengeTitle());
        assertNull(beggar.getDescription());
        assertEquals(0L, beggar.getSuccessCount());

    }

    @Test
    void findBeggar() {
    }

    @Test
    @DisplayName("Beggar nickname update Test")
    void updateBeggar() {
        //given
        String nickname = "testBeggar";
        String modNickname = "beggarTest";

        BeggarRequestDto beggarRequestDto = new BeggarRequestDto(modNickname);

        Beggar beggar = Beggar.builder()
                .nickname(nickname)
                .level(1L)
                .exp(0L)
                .point(0L)
                .build();

        //when

        beggar.update(beggarRequestDto);

        //then
        assertEquals(modNickname, beggar.getNickname());
        assertEquals(1L, beggar.getLevel());
        assertEquals(0L, beggar.getExp());
        assertEquals(0L, beggar.getPoint());
    }

    @Test
    @DisplayName("레벨로 경험치 가져오기 test")
    void getExpCheckTest() {
        //given
        Long level = 2L;

        //when
        Long nextExp = LevelType.getNextExpByLevel(level);

        //then
        assertEquals(nextExp, LevelType.LV2.getNextExp());
    }

    @Test
    @DisplayName("beggar 경험치 획득 하여 레벨업 test")
    void beggarUpdateExpTest() {
        //given
        String nickname = "TestBeggar";

        Beggar beggar = Beggar.builder()
                .nickname(nickname)
                .level(1L)
                .exp(0L)
                .point(0L)
                .build();

        Long exp = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();
        Long level = beggar.getLevel();
        Long point = beggar.getPoint() + ExpType.FILL_LEDGER.getAmount();

        //when
        if (LevelType.getNextExpByLevel(level) <= exp) {
            level ++;
        }

        beggar.updateExp(BeggarExpUpResponseDto.builder()
                        .exp(exp)
                        .level(level)
                        .point(point)
                        .build());

        //then
        assertEquals(100L, exp);
        assertEquals(100L, beggar.getExp());
        assertEquals(2L, beggar.getLevel());
        assertEquals(100L, beggar.getPoint());

    }

    @Test
    @DisplayName("beggar 경험치 두번 획득 test")
    void beggarUpdateExpDoubleTest2() {
        //given
        String nickname = "TestBeggar";

        Beggar beggar = Beggar.builder()
                .nickname(nickname)
                .level(1L)
                .exp(0L)
                .point(0L)
                .build();

        Long exp = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();
        Long level = beggar.getLevel();
        Long point = beggar.getPoint() + ExpType.FILL_LEDGER.getAmount();

        if (LevelType.getNextExpByLevel(level) <= exp) {
            level ++;
        }

        //when
        beggar.updateExp(BeggarExpUpResponseDto.builder()
                .exp(exp)
                .level(level)
                .point(point)
                .build());

        Long exp2 = beggar.getExp() + ExpType.FILL_LEDGER.getAmount();
        Long point2 = beggar.getPoint() + ExpType.FILL_LEDGER.getAmount();

        beggar.updateExp(BeggarExpUpResponseDto.builder()
                .exp(exp2)
                .level(level)
                .point(point2)
                .build());

        //then
        assertEquals(100L, exp);
        assertEquals(200L, beggar.getExp());
        assertEquals(200L, beggar.getPoint());
        assertEquals(2L, beggar.getLevel());

    }

    @Test
    @DisplayName("아이템 착용")
    public void customBeggar() {
        //given
        String nickname = "TestBeggar";

        Beggar beggar = Beggar.builder()
                .nickname(nickname)
                .level(1L)
                .exp(0L)
                .point(0L)
                .build();

        ItemListEnum itemListEnum = ItemListEnum.top_lv2_01;
        UnWearEnum unWearEnum = null;

        String itemType = itemListEnum.getItemType();

        switch (itemType) {
            case "top" -> beggar.updateCustomTops(itemListEnum);
            case "bottom" -> beggar.updateCustomBottoms(itemListEnum);
            case "shoes" -> beggar.updateCustomShoes(itemListEnum);
            case "acc" -> beggar.updateCustomAccessories(itemListEnum);
            case "custom" -> beggar.updateCustoms(itemListEnum);
            default -> throw new IllegalArgumentException("옳지 못한 행동입니다.");
        }

        assertEquals(itemListEnum, beggar.getTop());
        assertNull(beggar.getBottom());

    }

}