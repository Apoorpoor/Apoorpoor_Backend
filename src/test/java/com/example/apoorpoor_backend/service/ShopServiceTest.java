//package com.example.apoorpoor_backend.service;
//
//import com.example.apoorpoor_backend.dto.beggar.BeggarExpUpResponseDto;
//import com.example.apoorpoor_backend.model.Beggar;
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.enumType.ItemListEnum;
//import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//class ShopServiceTest {
//    @Test
//    @DisplayName("buy a product Test")
//    public void buyPointUpdate() {
//        //given
//        User user = new User("username", null, UserRoleEnum.USER, 2222222L, "kakaoName");
//        String nickname = "beggarTest";
//        ItemListEnum itemListEnum = ItemListEnum.top_lv2_01;
//        Beggar beggar = Beggar.builder()
//                .nickname(nickname)
//                .user(user)
//                .point(5000L)
//                .level(10L)
//                .exp(4500L)
//                .build();
//
//        Long updatePoint = beggar.getPoint() - itemListEnum.getItemPrice();
//
//        BeggarExpUpResponseDto beggarExpUpResponseDto = BeggarExpUpResponseDto.builder()
//                .nickname(nickname)
//                .exp(beggar.getExp())
//                .level(beggar.getLevel())
//                .point(updatePoint)
//                .build();
//
//        //when
//        beggar.updateExp(beggarExpUpResponseDto);
//
//        //then
//        assertEquals(user, beggar.getUser());
//        assertEquals(updatePoint, beggar.getPoint());
//        assertEquals(10L, beggar.getLevel());
//        assertEquals(4500L, beggar.getExp());
//
//    }
//
//}
