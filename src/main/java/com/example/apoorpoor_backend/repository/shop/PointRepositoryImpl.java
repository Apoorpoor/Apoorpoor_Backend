package com.example.apoorpoor_backend.repository.shop;

import com.example.apoorpoor_backend.dto.shop.PointResponseDto;
import com.example.apoorpoor_backend.dto.shop.PointSearchCondition;
import com.example.apoorpoor_backend.dto.shop.QPointResponseDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static com.example.apoorpoor_backend.model.QPoint.point;
import static io.jsonwebtoken.lang.Strings.hasText;
public class PointRepositoryImpl implements PointRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PointRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PointResponseDto> findAllByPeriodAndBeggar(Long beggarId, PointSearchCondition condition, Pageable pageable) {

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,point.createdAt
                , ConstantImpl.create("%Y-%m-%d")
        );

        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate;

        String type = condition.getDateType();

        if(type.equals("week")){
            startDate = endDate.minusWeeks(1);
        }else if(type.equals("month")){
            startDate = endDate.minusMonths(1);
        }else if(type.equals("3month")){
            startDate = endDate.minusMonths(3);
        }else if(type.equals("6month")){
            startDate = endDate.minusMonths(6);
        }else if(type.equals("year")){
            startDate = endDate.minusYears(1);
        }

        String startDateformat = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDateformat = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        BooleanBuilder builder = new BooleanBuilder();

        if(hasText(condition.getKind())){
            String kind = condition.getKind();
            if(kind.equals("earn")){
                builder.and(point.earnedPoint.isNotNull());
            }
            if(kind.equals("use")){
                builder.and(point.usedPoints.isNotNull());
            }
        }

        QueryResults<PointResponseDto> results = queryFactory
                .select(new QPointResponseDto(
                        point.id,
                        point.pointDescription,
                        point.earnedPoint,
                        point.usedPoints,
                        point.beggar.id,
                        point.createdAt
                ))
                .from(point)
                .where(
                        point.beggar.id.eq(beggarId),
                        formattedDate.between(startDateformat, endDateformat),
                        builder
                )
                .orderBy(point.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PointResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
