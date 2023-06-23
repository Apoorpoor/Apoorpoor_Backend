//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.dto.beggar.BeggarRequestDto;
//import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
//import com.example.apoorpoor_backend.security.UserDetailsImpl;
//import com.example.apoorpoor_backend.service.BeggarService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//public class BeggarRestControllerTest {
//
////    @BeforeEach
////    void setAuthentication() {
////        String username = "user";
////        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
////        Long kakaoId = 213213124L;
////        String kakaoname = "kakaoUser";
////        User user1 = new User(username, null, userRoleEnum, kakaoId, kakaoname);
////        UserDetailsImpl userDetails = new UserDetailsImpl(user1, user1.getUsername());
////
////        SecurityContext context = SecurityContextHolder.getContext();
////        context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
////    }
////
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    BeggarService beggarService;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @DisplayName("Beggar 생성 테스트")
//    void createBeggar() throws Exception {
//        BeggarRequestDto requestDto = new BeggarRequestDto("testNickname");
//        String username = "testname";
//        //UserDetailsImpl userDetails = new UserDetailsImpl(user.getUser(), user.getUsername());
//        StatusResponseDto responseDto = new StatusResponseDto("test message");
//        given(beggarService.createBeggar(any(), eq(username))).willReturn(new ResponseEntity<>(responseDto, HttpStatus.OK));
//
//        mockMvc.perform(post("/beggar")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsBytes(requestDto))
//                        //.content("{\"nickname\" :  \"beggar\"}")
//                )
//                .andExpect(status().isOk())
//               // .andExpect(jsonPath("$.meassage").exists())
//                //.andExpect(jsonPath("$.point").exists())
//                .andDo(print());
//    }
//
//}
