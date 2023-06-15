//package com.example.apoorpoor_backend.controller;
//
//import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryRequestDto;
//import com.example.apoorpoor_backend.dto.common.StatusResponseDto;
//import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
//import com.example.apoorpoor_backend.security.UserDetailsImpl;
//import com.example.apoorpoor_backend.service.LedgerHistoryService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.security.Principal;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//@WebMvcTest(LedgerHistoryController.class)
//class LedgerHistoryControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private LedgerHistoryService ledgerHistoryService;
//
//    @Mock
//    private UserDetailsImpl userDetails;
//
//
//    @DisplayName("LedgerHistory를 생성한다.")
//    @Test
//    void createLedgerHistory () throws Exception {
//        // Given
//        LedgerHistoryRequestDto requestDto = new LedgerHistoryRequestDto();
//
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(ledgerHistoryService.createLedgerHistory(any(LedgerHistoryRequestDto.class), eq("testuser")))
//                .thenReturn(ResponseEntity.ok(new StatusResponseDto("Success")));
//
//        // When
//        mockMvc.perform(MockMvcRequestBuilders.post("/ledgerhistory")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Success"));
//
//        // Then
//        verify(ledgerHistoryService, times(1)).createLedgerHistory(any(LedgerHistoryRequestDto.class), eq("testuser"));
//    }
//
//    @DisplayName("LedgerHistory를 업데이트한다.")
//    @Test
//    void updateLedgerHistory() throws Exception {
//        // Given
//        Long id = 1L;
//        LedgerHistoryRequestDto requestDto = new LedgerHistoryRequestDto();
//
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(ledgerHistoryService.updateLedgerHistory(eq(id), any(LedgerHistoryRequestDto.class), eq("testuser")))
//                .thenReturn(ResponseEntity.ok(new LedgerHistoryResponseDto()));
//
//        // When
//        mockMvc.perform(MockMvcRequestBuilders.put("/ledgerhistory/{id}", id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Then
//        verify(ledgerHistoryService, times(1)).updateLedgerHistory(eq(id), any(LedgerHistoryRequestDto.class), eq("testuser"));
//    }
//
//
//    @DisplayName("LedgerHistory를 조회한다.")
//    @Test
//    void getLedgerHistory() throws Exception {
//        // Given
//        Long id = 1L;
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(ledgerHistoryService.getLedgerHistory(eq(id), eq("testuser")))
//                .thenReturn(ResponseEntity.ok(new LedgerHistoryResponseDto()));
//
//        // When
//        mockMvc.perform(MockMvcRequestBuilders.get("/ledgerhistory/{id}", id)
//                        .principal((Principal) userDetails))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Then
//        verify(ledgerHistoryService, times(1)).getLedgerHistory(eq(id), eq("testuser"));
//    }
//
//    @DisplayName("LedgerHistory를 삭제한다.")
//    @Test
//    void deleteLedgerHistory() throws Exception {
//        // Given
//        Long id = 1L;
//        when(userDetails.getUsername()).thenReturn("testuser");
//        when(ledgerHistoryService.deleteLedgerHistory(eq(id), eq("testuser")))
//                .thenReturn(ResponseEntity.ok(new StatusResponseDto("Success")));
//
//        // When
//        mockMvc.perform(MockMvcRequestBuilders.delete("/ledgerhistory/{id}", id)
//                        .principal((Principal) userDetails))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        // Then
//        verify(ledgerHistoryService, times(1)).deleteLedgerHistory(eq(id), eq("testuser"));
//    }
//}