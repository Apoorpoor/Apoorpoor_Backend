package com.example.apoorpoor_backend.repository;

import com.example.apoorpoor_backend.dto.LedgerHistorySearchCondition;
import com.example.apoorpoor_backend.dto.LedgerHistoryListResponseDto;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface LedgerHistoryRepositoryCustom {

    List<LedgerHistoryListResponseDto> search(LedgerHistorySearchCondition condition);
}
