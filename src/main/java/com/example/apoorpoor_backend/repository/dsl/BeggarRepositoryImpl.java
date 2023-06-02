//package com.example.apoorpoor_backend.repository.dsl;
//
//import com.example.apoorpoor_backend.model.Beggar;
//import com.example.apoorpoor_backend.repository.BeggarRepositoryCustom;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import lombok.RequiredArgsConstructor;
//
//import java.util.Optional;
//
//import static com.example.apoorpoor_backend.model.QBeggar.beggar;
//import static com.example.apoorpoor_backend.model.QUser.user;
//
//@RequiredArgsConstructor
//public class BeggarRepositoryImpl implements BeggarRepositoryCustom {
//
//    private final JPAQueryFactory queryFactory;
//    @Override
//    public Optional<Beggar> findByUsername(String username) {
//        return Optional.ofNullable(queryFactory
//                .select(beggar)
//                .from(beggar)
//                .join(beggar.user, user)
//                .where(user.username.eq(username))
//                .fetchOne());
//    }
//}