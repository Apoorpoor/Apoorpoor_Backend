package com.example.apoorpoor_backend.repository.dsl;

import com.example.apoorpoor_backend.dto.LedgerHistoryListResponseDto;
import com.example.apoorpoor_backend.dto.LedgerHistorySearchCondition;
import com.example.apoorpoor_backend.repository.LedgerHistoryRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LedgerHistoryRepositoryImpl implements LedgerHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LedgerHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LedgerHistoryListResponseDto> search(LedgerHistorySearchCondition condition) {
        return null;
    }

}
