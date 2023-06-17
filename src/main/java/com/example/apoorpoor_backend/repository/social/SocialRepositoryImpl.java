package com.example.apoorpoor_backend.repository.social;

import com.example.apoorpoor_backend.dto.social.*;
import com.example.apoorpoor_backend.model.*;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.apoorpoor_backend.model.QAccount.account;
import static com.example.apoorpoor_backend.model.QBeggar.beggar;
import static com.example.apoorpoor_backend.model.QLedgerHistory.ledgerHistory;
import static com.example.apoorpoor_backend.model.QRanking.ranking;
import static com.example.apoorpoor_backend.model.QSocial.social;
import static com.example.apoorpoor_backend.model.QUser.user;

@Slf4j
public class SocialRepositoryImpl implements SocialRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public SocialRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Long getExpenditure(SocialSearchCondition condition, User findUser) {

        List<Long> accountIdList = getAccountIdList(findUser);

        AccountType accountType = condition.getAccountType();

        LocalDate yesterday = LocalDate.now().minusMonths(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                , ConstantImpl.create("%Y-%m")
        );

        return getExpenditureSum(formattedDate, previousMonth, accountIdList);

    }

    @Override
    public Long getIncome(SocialSearchCondition condition, User findUser) {

        List<Long> accountIdList = getAccountIdList(findUser);

        AccountType accountType = condition.getAccountType();

        LocalDate yesterday = LocalDate.now().minusMonths(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                , ConstantImpl.create("%Y-%m")
        );

        return getIncomeSum(formattedDate, previousMonth, accountIdList);
    }

    private Long getIncomeSum(StringTemplate formattedDate, String previousMonth, List<Long> accountIdList) {
        return queryFactory
                .select(ledgerHistory.income.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.INCOME),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();
    }

    @Override
    public List<ExpenditurePercentDto> getPercent(SocialSearchCondition condition, User findUser) {

        Long age = findUser.getAge();
        String gender = findUser.getGender();

        Long age_abb = age-(age%10);

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<ExpenditurePercentDto> result = queryFactory.
                select(new QExpenditurePercentDto(
                        new CaseBuilder()
                                .when(user.age.lt(20)).then(10L)
                                .when(user.age.lt(30)).then(20L)
                                .when(user.age.lt(40)).then(30L)
                                .when(user.age.lt(50)).then(40L)
                                .when(user.age.lt(60)).then(50L)
                                .when(user.age.lt(70)).then(60L)
                                .when(user.age.lt(80)).then(70L)
                                .when(user.age.lt(90)).then(80L)
                                .when(user.age.lt(100)).then(90L)
                                .otherwise(0L),
                        Expressions.stringTemplate("date_format({0}, '%Y-%m')", ledgerHistory.date),
                        ledgerHistory.expenditure.sum(),
                        user.gender,
                        user.id,
                        Expressions.template(Long.class, "dense_rank() over (order by sum(expenditure))")
                ))
                .from(account)
                .join(account.user, user)
                .join(account.ledgerHistories, ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        formattedDate.eq(date)
                )
                .groupBy(
                        new CaseBuilder()
                                .when(user.age.lt(20)).then(10L)
                                .when(user.age.lt(30)).then(20L)
                                .when(user.age.lt(40)).then(30L)
                                .when(user.age.lt(50)).then(40L)
                                .when(user.age.lt(60)).then(50L)
                                .when(user.age.lt(70)).then(60L)
                                .when(user.age.lt(80)).then(70L)
                                .when(user.age.lt(90)).then(80L)
                                .when(user.age.lt(100)).then(90L)
                                .otherwise(0L),
                        user.gender,
                        user.id
                ).fetch();
        return result;
    }

    @Override
    public Double getExpAverage(SocialSearchCondition condition, User findUser) {

        Long age = findUser.getAge();
        String gender = findUser.getGender();

        Long age_abb = age-(age%10);

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        return queryFactory
                .select(social.exp_avg.coalesce(0.0))
                .from(social)
                .where(
                        social.age_abb.eq(age_abb),
                        social.gender.eq(gender),
                        social.date.eq(date)
                )
                .fetchOne();
    }

    @Override
    public Double getIncAverage(SocialSearchCondition condition, User findUser) {
        Long age = findUser.getAge();
        String gender = findUser.getGender();

        Long age_abb = age-(age%10);

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        return queryFactory
                .select(social.inc_avg.coalesce(0.0))
                .from(social)
                .where(
                        social.age_abb.eq(age_abb),
                        social.gender.eq(gender),
                        social.date.eq(date)
                )
                .fetchOne();
    }

    @Override
    public List<IncomeTotalDto> getRankIncomeSum() {

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        return queryFactory
                .select(new QIncomeTotalDto(
                        ledgerHistory.date.stringValue(),
                        ledgerHistory.income.sum().as("incSum"),
                        beggar.id
                ))
                .from(ledgerHistory)
                .join(ledgerHistory.account, account)
                .join(account.user.beggar, beggar)
                .where(
                        ledgerHistory.accountType.eq(AccountType.INCOME),
                        formattedDate.eq(date)
                )
                .groupBy(beggar.id)
                .orderBy(ledgerHistory.income.sum().desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<ExpenditureTotalDto> getRankExpenditureSum() {

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        return queryFactory
                .select(new QExpenditureTotalDto(
                        ledgerHistory.date.stringValue(),
                        ledgerHistory.expenditure.sum().as("expSum"),
                        beggar.id
                ))
                .from(ledgerHistory)
                .join(ledgerHistory.account, account)
                .join(account.user.beggar, beggar)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        formattedDate.eq(date)
                )
                .groupBy(beggar.id)
                .orderBy(ledgerHistory.expenditure.sum().desc())
                .limit(10)
                .fetch();
    }

    @Override
    public List<ExpenditureAvgDto> getPercentExpenditureAvg() {

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<ExpenditureAvgDto> result = queryFactory.
                select(new QExpenditureAvgDto(
                        new CaseBuilder()
                                .when(user.age.lt(20)).then(10L)
                                .when(user.age.lt(30)).then(20L)
                                .when(user.age.lt(40)).then(30L)
                                .when(user.age.lt(50)).then(40L)
                                .when(user.age.lt(60)).then(50L)
                                .when(user.age.lt(70)).then(60L)
                                .when(user.age.lt(80)).then(70L)
                                .when(user.age.lt(90)).then(80L)
                                .when(user.age.lt(100)).then(90L)
                                .otherwise(0L),
                        Expressions.stringTemplate("date_format({0}, '%Y-%m')", ledgerHistory.date),
                        ledgerHistory.expenditure.countDistinct(),
                        ledgerHistory.expenditure.sum(),
                        ledgerHistory.expenditure.sum().doubleValue().divide(ledgerHistory.expenditure.countDistinct()),
                        user.gender
                ))
                .from(account)
                .join(account.user, user)
                .join(account.ledgerHistories, ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        formattedDate.eq(date)
                )
                .groupBy(
                        new CaseBuilder()
                                .when(user.age.lt(20)).then(10L)
                                .when(user.age.lt(30)).then(20L)
                                .when(user.age.lt(40)).then(30L)
                                .when(user.age.lt(50)).then(40L)
                                .when(user.age.lt(60)).then(50L)
                                .when(user.age.lt(70)).then(60L)
                                .when(user.age.lt(80)).then(70L)
                                .when(user.age.lt(90)).then(80L)
                                .when(user.age.lt(100)).then(90L)
                                .otherwise(0L),
                        user.gender
                ).fetch();

        return result;
    }

    @Override
    public List<IncomeAvgDto> getPercentIncomeAvg() {

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<IncomeAvgDto> result = queryFactory.
                select(new QIncomeAvgDto(
                        new CaseBuilder()
                                .when(user.age.lt(20)).then(10L)
                                .when(user.age.lt(30)).then(20L)
                                .when(user.age.lt(40)).then(30L)
                                .when(user.age.lt(50)).then(40L)
                                .when(user.age.lt(60)).then(50L)
                                .when(user.age.lt(70)).then(60L)
                                .when(user.age.lt(80)).then(70L)
                                .when(user.age.lt(90)).then(80L)
                                .when(user.age.lt(100)).then(90L)
                                .otherwise(0L),
                        Expressions.stringTemplate("date_format({0}, '%Y-%m')", ledgerHistory.date),
                        ledgerHistory.income.countDistinct(),
                        ledgerHistory.income.sum(),
                        ledgerHistory.income.sum().doubleValue().divide(ledgerHistory.income.countDistinct()),
                        user.gender
                ))
                .from(account)
                .join(account.user, user)
                .join(account.ledgerHistories, ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.INCOME),
                        formattedDate.eq(date)
                )
                .groupBy(
                        new CaseBuilder()
                                .when(user.age.lt(20)).then(10L)
                                .when(user.age.lt(30)).then(20L)
                                .when(user.age.lt(40)).then(30L)
                                .when(user.age.lt(50)).then(40L)
                                .when(user.age.lt(60)).then(50L)
                                .when(user.age.lt(70)).then(60L)
                                .when(user.age.lt(80)).then(70L)
                                .when(user.age.lt(90)).then(80L)
                                .when(user.age.lt(100)).then(90L)
                                .otherwise(0L),
                        user.gender
                ).fetch();

        return result;
    }

    @Override
    public Social findByAgeAndDateAndGender(Long ageAbb, String date, String gender) {

        return queryFactory
                .select(social)
                .from(social)
                .where(
                        social.age_abb.eq(ageAbb),
                        social.date.eq(date),
                        social.gender.eq(gender)
                )
                .fetchOne();
    }

    @Override
    public Long getExpSum(SocialSearchCondition condition, User findUser) {
        Long age = findUser.getAge();
        String gender = findUser.getGender();

        Long age_abb = age-(age%10);

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        return queryFactory
                .select(social.exp_sum.coalesce(0L))
                .from(social)
                .where(
                        social.age_abb.eq(age_abb),
                        social.gender.eq(gender),
                        social.date.eq(date)
                )
                .fetchOne();
    }

    @Override
    public Long getIncSum(SocialSearchCondition condition, User findUser) {
        Long age = findUser.getAge();
        String gender = findUser.getGender();

        Long age_abb = age-(age%10);

        LocalDate minusMonths = LocalDate.now().minusMonths(1);
        String date = minusMonths.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        return queryFactory
                .select(social.inc_sum.coalesce(0L))
                .from(social)
                .where(
                        social.age_abb.eq(age_abb),
                        social.gender.eq(gender),
                        social.date.eq(date)
                )
                .fetchOne();
    }

    @Override
    public List<Ranking> getRank(SocialSearchCondition condition) {

        return queryFactory
                .select(ranking)
                .from(ranking)
                .where(
                        accountTypeRankingEq(condition.getAccountType())
                )
                .fetch();
    }

    public Long getExpenditureSum(StringTemplate formattedDate, String previousMonth, List<Long> accountIdList){

        return queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();
    }

    private BooleanExpression accountTypeEq(AccountType accountType){
        return accountType != null ? ledgerHistory.accountType.eq(accountType) : null;
    }

    private BooleanExpression accountTypeRankingEq(AccountType accountType){
        return accountType != null ? ranking.accountType.eq(accountType) : null;
    }

    private List<Long> getAccountIdList(User findUser) {
        return queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(findUser.getId()))
                .fetch();
    }
}
