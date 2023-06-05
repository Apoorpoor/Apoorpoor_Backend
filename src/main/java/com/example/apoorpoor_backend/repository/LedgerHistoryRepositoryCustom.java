package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.dto.LedgerHistorySearchCondition;
import com.example.apoorpoor_backend.dto.LedgerHistoryListResponseDto;
import com.example.apoorpoor_backend.dto.MyPageResponseDto;
import com.example.apoorpoor_backend.dto.MyPageSearchCondition;

import java.util.List;
public interface LedgerHistoryRepositoryCustom {

    List<LedgerHistoryListResponseDto> search(LedgerHistorySearchCondition condition);

    List<MyPageResponseDto> getMypageStatus(MyPageSearchCondition condition, Long userId);
    List<MyPageResponseDto> getRecentStatus(Long userId);
}
