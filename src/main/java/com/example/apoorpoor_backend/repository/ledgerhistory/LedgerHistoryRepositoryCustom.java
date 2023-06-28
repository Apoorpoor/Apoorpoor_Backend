package com.example.apoorpoor_backend.repository.ledgerhistory;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.List;
public interface LedgerHistoryRepositoryCustom {

    List<TotalSumResponseDto> getMypageStatus(Long userId);

    List<ExpenditureSumResponseDto> getExpenditureRecentStatus(Long userId);

    List<IncomeSumResponseDto> getIncomeRecentStatus(Long userId);

    List<AccountTotalResponseDto> getTotalStatus(Long accountId, AccountSearchCondition condition);

    Page<LedgerHistoryResponseDto> getStatus(Long accountId, AccountSearchCondition condition, Pageable pageable);

    Page<MonthSumResponseDto> getStatistic(Long accountId, AccountSearchCondition condition, Pageable pageable);

    MonthSumResponseDto getDifference(Long accountId, AccountSearchCondition condition, LocalDate targetDate, int quarter);

    boolean checkEXPType2(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType3(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType4(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType5(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType7(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType8(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType9(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType10(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType11(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType12(ExpenditureType expenditureType, Long userId);

}
