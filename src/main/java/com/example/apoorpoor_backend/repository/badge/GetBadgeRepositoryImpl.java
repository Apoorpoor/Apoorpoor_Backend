package com.example.apoorpoor_backend.repository.badge;

import com.example.apoorpoor_backend.model.GetBadge;
import com.example.apoorpoor_backend.model.QGetBadge;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.example.apoorpoor_backend.model.QGetBadge.getBadge;

public class GetBadgeRepositoryImpl implements GetBadgeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public GetBadgeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public long countByBeggar(Long beggarId) {

        JPAQuery<GetBadge> result = queryFactory
                .select(getBadge)
                .from(getBadge)
                .where(getBadge.beggar.id.eq(beggarId));

        return result.fetchCount();
    }
}
