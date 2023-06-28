//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.dto.beggar.*;
//import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
//import com.example.apoorpoor_backend.model.Badge;
//import com.example.apoorpoor_backend.model.User;
//import com.example.apoorpoor_backend.model.enumType.UserRoleEnum;
//import com.example.apoorpoor_backend.security.UserDetailsImpl;
//import com.example.apoorpoor_backend.service.BeggarService;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//class BeggarControllerTest {
//    private static UserDetailsImpl user;
//
//    @Autowired
//    private BeggarController beggarController;
//
//    @MockBean
//    private BeggarService beggarService;
//
//    @BeforeAll
//    static void setUp() {
//        String username = "user";
//        UserRoleEnum userRoleEnum = UserRoleEnum.USER;
//        Long kakaoId = 213213124L;
//        User user1 = new User(username, null, userRoleEnum, kakaoId);
//        user = new UserDetailsImpl(user1, user1.getUsername());
//    }
//
//    @BeforeEach
//    void setAuthentication() {
//        SecurityContext context = SecurityContextHolder.getContext();
//        context.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
//    }
//
//    @BeforeEach
//    void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(beggarController).build();
//    }
//
//    private MockMvc mockMvc;
//
//    @Test
//    @DisplayName("createBeggarCall Test")
//    public void createBeggarCall() throws Exception {
//        mockMvc.perform(post("/beggar")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"name\" :  \"beggar\"}")
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("createBeggar Test")
//    void createBeggar() {
//        // Mock 주입
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        BeggarRequestDto mockedRequestDto = new BeggarRequestDto();
//        UserDetailsImpl mockedUserDetails = mock(UserDetailsImpl.class);
//        StatusResponseDto mockedResponseDto = new StatusResponseDto("푸어가  생성 되었어요...");
//
//        // service 의 메서드가 호출될때 임의의 파라미터로 응답 확인
//        ResponseEntity<StatusResponseDto> expectedResponse = new ResponseEntity<>(mockedResponseDto, HttpStatus.OK);
//        when(mockedUserDetails.getUsername()).thenReturn("username");
//        when(mockedBeggarService.createBeggar(any(), eq("username"))).thenReturn(expectedResponse);
//
//        // controller 인스턴스 생성
//        BeggarController controller = new BeggarController(mockedBeggarService);
//
//        // controller 메서드 호출
//        ResponseEntity<StatusResponseDto> response = controller.createBeggar(mockedRequestDto, mockedUserDetails);
//
//        // 응답 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//        assertEquals(expectedResponse, response);
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).createBeggar(any(), eq("username"));
//    }
//
//    @Test
//    @DisplayName("myBeggarCall Test")
//    public void myBeggarCall() throws Exception {
//        mockMvc.perform(get("/beggar")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("myBeggar Test")
//    void myBeggar() {
//        // Mock 객체 생성
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        UserDetailsImpl mockedUserDetails = mock(UserDetailsImpl.class);
//
//        BeggarSearchResponseDto expectedResponseDto = BeggarSearchResponseDto.builder()
//                .beggarId(1L)
//                .userId(2L)
//                .nickname("Tester")
//                .point(100L)
//                .exp(500L)
//                .badgeList(List.of(new Badge(1L, "badge1", "badgeImage1"), new Badge(2L, "badge2", "badgeImage2")))
//                .level(5L)
//                .description("Description")
//                .gender("Male")
//                .age(30L)
//                .topImage("topImage.jpg")
//                .bottomImage("bottomImage.jpg")
//                .shoesImage("shoesImage.jpg")
//                .accImage("accImage.jpg")
//                .customImage("customImage.jpg")
//                .build();
//
//        //테스트 예상 결과
//        ResponseEntity<BeggarSearchResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);
//
//        //컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // 임의의 파라미터로 응답 확인
//        when(mockedUserDetails.getUsername()).thenReturn("username");
//        when(mockedBeggarService.myBeggar(eq("username"))).thenReturn(expectedResponse);
//
//        // 응답객체 저장
//        ResponseEntity<BeggarSearchResponseDto> response = mockedBeggarController.myBeggar(mockedUserDetails);
//
//        // 실제 응답과 예상응답 비교
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//        assertEquals(expectedResponse, response);
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).myBeggar(eq("username"));
//    }
//
//    @Test
//    @DisplayName("getUserBeggarCall Test")
//    public void getUserBeggarCall() throws Exception {
//        mockMvc.perform(get("/beggar/{userId}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("getUserBeggar Test")
//    void getUserBeggar() {
//        // Mock 객체 생성
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//
//        BeggarSearchResponseDto expectedResponseDto = BeggarSearchResponseDto.builder()
//                .beggarId(1L)
//                .userId(2L)
//                .nickname("Tester")
//                .point(100L)
//                .exp(500L)
//                .badgeList(List.of(new Badge(1L, "badge1", "badgeImage1"), new Badge(2L, "badge2", "badgeImage2")))
//                .level(5L)
//                .description("Description")
//                .gender("Male")
//                .age(30L)
//                .topImage("topImage.jpg")
//                .bottomImage("bottomImage.jpg")
//                .shoesImage("shoesImage.jpg")
//                .accImage("accImage.jpg")
//                .customImage("customImage.jpg")
//                .build();
//
//        // 테스트 예상 결과
//        ResponseEntity<BeggarSearchResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);
//
//        // 컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // 임의의 파라미터로 응답 확인
//        when(mockedBeggarService.getUserBeggar(anyLong())).thenReturn(expectedResponse);
//
//        // 응답 객체 저장
//        ResponseEntity<BeggarSearchResponseDto> response = mockedBeggarController.getUserBeggar(anyLong());
//
//        // 테스트 결과 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//        assertEquals(expectedResponse, response);
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).getUserBeggar(anyLong());
//    }
//
//    @Test
//    @DisplayName("updateBeggarCall Test")
//    public void updateBeggarCall() throws Exception {
//        mockMvc.perform(patch("/beggar")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"nickname\" : \"beggar\"}")
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("updateBeggar Test")
//    void updateBeggar() {
//        // Mock 객체 생성
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        UserDetailsImpl mockedUserDetails = mock(UserDetailsImpl.class);
//        BeggarRequestDto requestDto = new BeggarRequestDto();
//
//        BeggarResponseDto expectedResponseDto = BeggarResponseDto.builder()
//                .beggar_id(1L)
//                .user_id(1L)
//                .point(10L)
//                .nickname("Tester")
//                .level(3L)
//                .description("CommentTest")
//                .build();
//
//        // Test 예상 결과
//        ResponseEntity<BeggarResponseDto> expectedResponse = new ResponseEntity<>(expectedResponseDto, HttpStatus.OK);
//
//        // 컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // when()을 사용하여 메서드 호출에 대한 응답을 지정
//        when(mockedUserDetails.getUsername()).thenReturn("username");
//        when(mockedBeggarService.updateBeggar(eq(requestDto), eq("username"))).thenReturn(expectedResponse);
//
//        // 응답 객체 저장
//        ResponseEntity<BeggarResponseDto> response = mockedBeggarController.updateBeggar(requestDto, mockedUserDetails);
//
//        // 테스트 결과 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//        assertEquals(expectedResponse, response);
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).updateBeggar(eq(requestDto), eq("username"));
//    }
//
//    @Test
//    @DisplayName("customBeggarCall Test(put on)")
//    public void customBeggarCall_putON() throws Exception {
//        mockMvc.perform(patch("/beggar/custom")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"itemListEnum\": \"top_lv2_01\", \"unWearEnum\": null }")
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("customBeggarCall Test(take off)")
//    public void customBeggarCall_takeOff() throws Exception {
//        mockMvc.perform(patch("/beggar/custom")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"itemListEnum\": \"top_lv2_01\", \"unWearEnum\": \"top\" }")
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("customBeggar Test")
//    void customBeggar() {
//        // Mock 객체 생성
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        UserDetailsImpl mockedUserDetails = mock(UserDetailsImpl.class);
//        BeggarCustomRequestDto requestDto = new BeggarCustomRequestDto();
//
//        // Test 예상 결과
//        ResponseEntity<String> expectedResponse = new ResponseEntity<>("착용 완료", HttpStatus.OK);
//
//        // 컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // when()을 사용하여 메서드 호출에 대한 응답을 지정
//        when(mockedUserDetails.getUsername()).thenReturn("username");
//        when(mockedBeggarService.customBeggar(eq(requestDto), eq("username"))).thenReturn(expectedResponse);
//
//        // 응답 객체 저장
//        ResponseEntity<String> response = mockedBeggarController.customBeggar(requestDto, mockedUserDetails);
//
//        // 테스트 결과 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//        assertEquals(expectedResponse, response);
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).customBeggar(eq(requestDto), eq("username"));
//    }
//
//    @Test
//    @DisplayName("customListCall Test")
//    public void customListCall() throws Exception {
//        mockMvc.perform(get("/beggar/custom")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .principal(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    @DisplayName("customList Test")
//    void customList() {
//        // Mock 객체 생성
//        BeggarService mockedBeggarService = mock(BeggarService.class);
//        UserDetailsImpl mockedUserDetails = mock(UserDetailsImpl.class);
//        BeggarCustomRequestDto requestDto = new BeggarCustomRequestDto();
//        BeggarCustomListResponseDto responseDto = new BeggarCustomListResponseDto();
//
//        // Test 예상결과
//        ResponseEntity<BeggarCustomListResponseDto> expectedResponse = new ResponseEntity<>(responseDto, HttpStatus.OK);
//
//        // 컨트롤러 인스턴스 생성
//        BeggarController mockedBeggarController = new BeggarController(mockedBeggarService);
//
//        // when()을 사용하여 메서드 호출에 대한 응답을 지정
//        when(mockedUserDetails.getUsername()).thenReturn("username");
//        when(mockedBeggarService.customList(eq("username"))).thenReturn(expectedResponse);
//
//        // 응답 객체 저장
//        ResponseEntity<BeggarCustomListResponseDto> response = mockedBeggarController.customList(mockedUserDetails);
//
//        // 테스트 결과 검증
//        assertEquals(expectedResponse.getStatusCode(), response.getStatusCode());
//        assertEquals(expectedResponse.getBody(), response.getBody());
//        assertEquals(expectedResponse, response);
//
//        // Mock 객체로 서비스 메서드의 호출 확인
//        verify(mockedBeggarService).customList(eq("username"));
//    }
//}