package com.example.apoorpoor_backend.repository.badge;

import com.example.apoorpoor_backend.model.Badge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import static com.example.apoorpoor_backend.model.QBadge.badge;
import static com.example.apoorpoor_backend.model.QGetBadge.*;

public class BadgeRepositoryImpl implements BadgeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public BadgeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Badge> findByBadgeList(Long beggarId) {

        List<Badge> result = queryFactory.selectFrom(badge)
                .innerJoin(getBadge).on(badge.id.eq(getBadge.badge.id))
                .where(getBadge.beggar.id.eq(beggarId))
                .fetch();

        return result;
    }
}