package com.example.apoorpoor_backend.controller;

import com.example.apoorpoor_backend.dto.AgeRequestDto;
import com.example.apoorpoor_backend.dto.GenderRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.openid.connect.sdk.claims.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @DisplayName("나이를 설정한다.")
    @Test
    void setAge() throws Exception {
        //given
        AgeRequestDto requestDto = AgeRequestDto.builder()
                .age(28L)
                .build();

        //when //then
        mockMvc.perform(MockMvcRequestBuilders.put("/user/age")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("성별을 설정한다.")
    @Test
    void setGender() throws Exception {
        //given
        GenderRequestDto requestDto = GenderRequestDto.builder()
                .gender("female")
                .build();

        //when then
        mockMvc.perform(MockMvcRequestBuilders.put("/user/gender")
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("유저 정보를 조회한다.")
    @Test
    void userInfo() throws Exception {
        //given

        //when

        //then
    }

}