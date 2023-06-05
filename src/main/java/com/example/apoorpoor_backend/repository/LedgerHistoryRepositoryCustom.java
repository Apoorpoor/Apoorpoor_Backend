package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.dto.*;

import java.util.List;
public interface LedgerHistoryRepositoryCustom {

    List<LedgerHistoryListResponseDto> search(LedgerHistorySearchCondition condition);

    List<MonthSumResponseDto> getMypageStatus(MyPageSearchCondition condition, Long userId);
    List<MonthSumResponseDto> getRecentStatus(Long userId);

    List<AccountTotalResponseDto> getTotalStatus(Long accountId, AccountSearchCondition condition);

    List<LedgerHistoryResponseDto> getStatus(Long accountId, AccountSearchCondition condition);

    List<MonthSumResponseDto> getStatistic(Long accountId, AccountSearchCondition condition);

    List<MonthSumResponseDto> getDifference(Long accountId);
}
