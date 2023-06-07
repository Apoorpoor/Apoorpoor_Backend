package com.example.apoorpoor_backend.repository.beggar;

import com.example.apoorpoor_backend.model.Badge;
import com.example.apoorpoor_backend.model.Beggar;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.example.apoorpoor_backend.model.QBeggar.beggar;
import static com.example.apoorpoor_backend.model.QUser.user;

public class BeggarRepositoryImpl implements BeggarRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BeggarRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Optional<Beggar> findByUsername(String username) {
        return Optional.ofNullable(queryFactory
                .select(beggar)
                .from(beggar)
                .join(beggar.user, user)
                .where(user.username.eq(username))
                .fetchOne());
    }
}