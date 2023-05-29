//package com.example.apoorpoor_backend.service;
//
//import com.example.apoorpoor_backend.dto.LedgerRequestDto;
//import com.example.apoorpoor_backend.dto.LedgerResponseDto;
//import com.example.apoorpoor_backend.entity.AccountType;
//import com.example.apoorpoor_backend.entity.Balance;
//import com.example.apoorpoor_backend.entity.FinancialLedger;
//import com.example.apoorpoor_backend.entity.User;
//import com.example.apoorpoor_backend.repository.BalanceRepository;
//import com.example.apoorpoor_backend.repository.FinancialLedgerRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collections;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class FinancialLedgerServiceTest {
//
//    @Mock
//    private FinancialLedgerRepository financialLedgerRepository;
//
//    @Mock
//    private BalanceRepository balanceRepository;
//
//    @InjectMocks
//    private FinancialLedgerService financialLedgerService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    @DisplayName("가계부 목록 생성")
//    public void testCreateLedgerIncomeAccount() {
//        //Mock 데이터 설정
//        String userName = "testMember";
//        String ledgerTitle = "Test Ledger";
//        String incomeType = "Salary";
//        Long income = 50000L;
//        AccountType accountType = AccountType.INCOME;
//
//        //인증 정보 설정
//        UserDetails userDetails = (UserDetails) new User(userName, "password");
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
//
//        //테스트용 DTO 생성
//        LedgerRequestDto ledgerRequestDto = LedgerRequestDto.builder()
//                .ledgerTitle(ledgerTitle)
//                .incomeType(incomeType)
//                .income(income)
//                .accountType(accountType).build();
//
//        //balanceRepository.findByLedgerTitle() 메서드의 반환값 설정
//        Optional<Balance> findBalance = Optional.empty();
//        when(balanceRepository.findByLedgerTitle(ledgerTitle)).thenReturn(findBalance);
//
//        //financialLedgerRepository.save() 메서드의 인자를 캡처하기 위한 ArgumentCaptor
//        ArgumentCaptor<FinancialLedger> financialLedgerArgumentCaptor = ArgumentCaptor.forClass(FinancialLedger.class);
//
//        //createLedger() 호출
//        ResponseEntity<LedgerResponseDto> response = financialLedgerService.createLedger(ledgerRequestDto, authentication);
//
//        //반환값 확인
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        LedgerResponseDto ledgerResponseDto = response.getBody();
//        assertEquals(memberId, ledgerResponseDto.getMemberId());
//        assertEquals(ledgerTitle, ledgerResponseDto.getLedgerTitle());
//        assertEquals(incomeType, ledgerResponseDto.getIncomeType());
//        assertEquals(null, ledgerResponseDto.getExpenditureType());
//        assertEquals(income, ledgerResponseDto.getIncome());
//        assertEquals(0L, ledgerResponseDto.getExpenditure());
//        assertEquals(accountType, ledgerResponseDto.getAccountType());
//
//        //financialLedgerRepository.save() 메서드가 올바른 인자로 호출되었는지 검증
//        verify(financialLedgerRepository, times(1)).save(financialLedgerArgumentCaptor.capture());
//        FinancialLedger savedFinancialLedger = financialLedgerArgumentCaptor.getValue();
//        assertEquals(memberId, savedFinancialLedger.getMemberId());
//        assertEquals(ledgerTitle, savedFinancialLedger.getLedgerTitle());
//        assertEquals(incomeType, savedFinancialLedger.getIncomeType());
//        assertEquals(null, savedFinancialLedger.getExpenditureType());
//        assertEquals(income, savedFinancialLedger.getIncome());
//        assertEquals(0L, savedFinancialLedger.getExpenditure());
//        assertEquals(accountType, ledgerResponseDto.getAccountType());
//
//    }
//
//
//}