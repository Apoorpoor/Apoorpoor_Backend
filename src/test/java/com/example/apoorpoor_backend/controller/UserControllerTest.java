//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.dto.user.AgeRequestDto;
//import com.example.apoorpoor_backend.dto.user.GenderRequestDto;
//import com.example.apoorpoor_backend.dto.user.UserResponseDto;
//import com.example.apoorpoor_backend.security.UserDetailsImpl;
//import com.example.apoorpoor_backend.service.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//class UserControllerTest {
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private UserDetailsImpl userDetails;
//
//    @InjectMocks
//    private UserController userController;
//
//    @DisplayName("나이를 설정한다.")
//    @Test
//    void setAgeTest() throws Exception {
//        //given
//        AgeRequestDto ageRequestDto = AgeRequestDto.builder()
//                .age(28L)
//                .build();
//
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(userService.setAge(28L, "testuser")).thenReturn(new ResponseEntity<>(28L, HttpStatus.OK));
//
//        //when
//        ResponseEntity<Long> response = userController.setAge(ageRequestDto, userDetails);
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(28L, response.getBody());
//
//        verify(userService, times(1)).setAge(28L, "testuser");
//    }
//
//    @DisplayName("존재하지 않는 유저는 나이를 설정할 수 없다.")
//    @Test
//    void setAgeTest_InvalidUser() throws Exception {
//        // given
//        AgeRequestDto ageRequestDto = AgeRequestDto.builder()
//                .age(25L)
//                .build();
//
//        when(userDetails.getUsername()).thenReturn("invaliduser");
//        when(userService.setAge(25L, "invaliduser")).thenThrow(new UsernameNotFoundException("User not found"));
//
//        // when
//        ResponseEntity<Long> response = userController.setAge(ageRequestDto, userDetails);
//
//        // then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//
//        verify(userService, times(1)).setAge(25L, "invaliduser");
//    }
//
//    @DisplayName("성별을 설정한다.")
//    @Test
//    void setGenderTest() throws Exception {
//        //given
//        GenderRequestDto genderRequestDto = GenderRequestDto.builder()
//                .gender("female")
//                .build();
//
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(userService.setGender("female", "testuser")).thenReturn(new ResponseEntity<>("female", HttpStatus.OK));
//
//        //when
//        ResponseEntity<String> response = userController.setGender(genderRequestDto, userDetails);
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("female", response.getBody());
//
//        verify(userService, times(1)).setGender("female", "testuser");
//    }
//
//    @DisplayName("존재하지 않는 유저는 성별을 설정할 수 없다.")
//    @Test
//    void setGenderTest_InvalidUser() throws Exception {
//        //given
//        GenderRequestDto genderRequestDto = GenderRequestDto.builder()
//                .gender("female")
//                .build();
//
//        when(userDetails.getUsername()).thenReturn("invalidUser");
//        when(userService.setGender("female", "invalidUser")).thenThrow(new UsernameNotFoundException("User not found"));
//
//        //when
//        ResponseEntity<String> response = userController.setGender(genderRequestDto, userDetails);
//
//        //then
//        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//        assertNull(response.getBody());
//
//        verify(userService, times(1)).setGender("female", "invalidUser");
//    }
//
//    @DisplayName("사용자의 정보를 조회한다.")
//    @Test
//    void userInfoTest() throws Exception {
//
//        //given
//        UserResponseDto userResponseDto = new UserResponseDto(1L, "user", 1212121L, 28L, "female");
//
//        when(userDetails.getUsername()).thenReturn("user");
//        when(userService.userInfo("user")).thenReturn(new ResponseEntity<>(userResponseDto, HttpStatus.OK));
//
//        //when
//        ResponseEntity<UserResponseDto> response = userController.userInfo(userDetails);
//
//        //then
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(userResponseDto, response.getBody());
//
//        verify(userService, times(1)).userInfo("user");
//    }
//
//
//    @DisplayName("나이를 설정한다.")
//    @Test
//    void setAge() throws Exception {
//        //given
//        AgeRequestDto requestDto = AgeRequestDto.builder()
//                .age(28L)
//                .build();
//
//        //when //then
//        mockMvc.perform(MockMvcRequestBuilders.put("/user/age")
//                        .content(objectMapper.writeValueAsString(requestDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @DisplayName("성별을 설정한다.")
//    @Test
//    void setGender() throws Exception {
//        //given
//        GenderRequestDto requestDto = GenderRequestDto.builder()
//                .gender("female")
//                .build();
//
//        //when then
//        mockMvc.perform(MockMvcRequestBuilders.put("/user/gender")
//                        .content(objectMapper.writeValueAsString(requestDto))
//                        .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//
//}
