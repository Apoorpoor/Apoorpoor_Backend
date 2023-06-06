//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.dto.account.AccountRequestDto;
//import com.example.apoorpoor_backend.dto.account.AccountResponseDto;
//import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
//import com.example.apoorpoor_backend.security.UserDetailsImpl;
//import com.example.apoorpoor_backend.service.AccountService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.http.RequestEntity.post;
//
//@RunWith(MockitoJUnitRunner.class)
//public class AccountControllerTest {
//
//    @InjectMocks
//    private AccountController accountController;
//
//    @Mock
//    private AccountService accountService;
//
//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
//    }
//
//    @Test
//    public void testCreateAccount() throws Exception {
//        // 준비
//        AccountRequestDto requestDto = new AccountRequestDto();
//        // requestDto 설정
//
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        // userDetails 설정
//
//        StatusResponseDto responseDto = new StatusResponseDto();
//        // responseDto 설정
//
//        when(accountService.createAccount(requestDto, userDetails.getUsername())).thenReturn(ResponseEntity.ok(responseDto));
//
//        // 실행 및 검증
//        mockMvc.perform(post("/account")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDto))
//                        .principal(userDetails))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(responseDto.getStatus()));
//
//        verify(accountService).createAccount(requestDto, userDetails.getUsername());
//    }
//
//    @Test
//    public void testGetAllAccounts() throws Exception {
//        // 준비
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        // userDetails 설정
//
//        List<AccountResponseDto> accountList = new ArrayList<>();
//        // accountList에 필요한 데이터 추가
//
//        when(accountService.getAllAccounts(userDetails.getUsername())).thenReturn(ResponseEntity.ok(accountList));
//
//        // 실행 및 검증
//        mockMvc.perform(get("/accounts")
//                        .principal(userDetails))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(accountList.size())));
//
//        verify(accountService).getAllAccounts(userDetails.getUsername());
//    }
//
//    @Test
//    public void testGetAccount() throws Exception {
//        // 준비
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        // userDetails 설정
//
//        Long accountId = 1L;
//
//        AccountResponseDto responseDto = new AccountResponseDto();
//        // responseDto 설정
//
//        when(accountService.getAccount(accountId, userDetails.getUsername())).thenReturn(ResponseEntity.ok(responseDto));
//
//        // 실행 및 검증
//        mockMvc.perform(get("/accounts/{id}", accountId)
//                        .principal(userDetails))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(responseDto.getId()));
//
//        verify(accountService).getAccount(accountId, userDetails.getUsername());
//    }
//
//    @Test
//    public void testUpdateAccount() throws Exception {
//        // 준비
//        AccountRequestDto requestDto = new AccountRequestDto();
//        // requestDto 설정
//
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        // userDetails 설정
//
//        Long accountId = 1L;
//
//        AccountResponseDto responseDto = new AccountResponseDto();
//        // responseDto 설정
//
//        when(accountService.updateAccount(accountId, requestDto, userDetails.getUsername())).thenReturn(ResponseEntity.ok(responseDto));
//
//        // 실행 및 검증
//        mockMvc.perform(patch("/accounts/{id}", accountId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDto))
//                        .principal(userDetails))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(responseDto.getId()));
//
//        verify(accountService).updateAccount(accountId, requestDto, userDetails.getUsername());
//    }
//
//    @Test
//    public void testDeleteAccount() throws Exception {
//        // 준비
//        UserDetailsImpl userDetails = new UserDetailsImpl();
//        // userDetails 설정
//
//        Long accountId = 1L;
//
//        StatusResponseDto responseDto = new StatusResponseDto();
//        // responseDto 설정
//
//        when(accountService.deleteAccount(accountId, userDetails.getUsername())).thenReturn(ResponseEntity.ok(responseDto));
//
//        // 실행 및 검증
//        mockMvc.perform(delete("/accounts/{id}", accountId)
//                        .principal(userDetails))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.status").value(responseDto.getStatus()));
//
//        verify(accountService).deleteAccount(accountId, userDetails.getUsername());
//    }
//}