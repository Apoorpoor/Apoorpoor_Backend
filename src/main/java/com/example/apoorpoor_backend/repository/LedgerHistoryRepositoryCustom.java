package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.dto.*;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;

import java.util.List;
public interface LedgerHistoryRepositoryCustom {

    List<LedgerHistoryListResponseDto> search(LedgerHistorySearchCondition condition);

    List<MonthSumResponseDto> getMypageStatus(MyPageSearchCondition condition, Long userId);
    List<MonthSumResponseDto> getRecentStatus(Long userId);

    List<AccountTotalResponseDto> getTotalStatus(Long accountId, AccountSearchCondition condition);

    List<LedgerHistoryResponseDto> getStatus(Long accountId, AccountSearchCondition condition);

    List<MonthSumResponseDto> getStatistic(Long accountId, AccountSearchCondition condition);

    List<MonthSumResponseDto> getDifference(Long accountId);
    
    boolean checkEXPType1(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType2(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType3(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType4(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType5(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType6(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType7(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType8(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType9(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType10(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType11(ExpenditureType expenditureType, Long userId);

    boolean checkEXPType12(ExpenditureType expenditureType, Long userId);
}
