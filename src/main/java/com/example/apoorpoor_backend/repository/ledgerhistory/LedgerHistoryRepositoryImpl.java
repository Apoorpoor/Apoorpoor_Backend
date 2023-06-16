package com.example.apoorpoor_backend.repository.ledgerhistory;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.QLedgerHistoryResponseDto;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.apoorpoor_backend.model.QAccount.account;
import static com.example.apoorpoor_backend.model.QLedgerHistory.ledgerHistory;

@Slf4j
public class LedgerHistoryRepositoryImpl implements LedgerHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LedgerHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
    
    @Override
    public List<TotalSumResponseDto> getMypageStatus(Long userId) {
        List<Long> accountIdList = getAccountIdList(userId);

        return queryFactory
                .select(new QTotalSumResponseDto(
                        ledgerHistory.expenditureType,
                        ledgerHistory.expenditure.sum().as("total_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.in(accountIdList)
                )
                .groupBy(ledgerHistory.expenditureType)
                .fetch();
    }


    @Override
    public List<MonthSumResponseDto> getRecentStatus(Long userId) {
        
        List<Long> accountIdList = getAccountIdList(userId);
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        return queryFactory
                .select(new QMonthSumResponseDto(
                        formattedDate.as("month"),
                        ledgerHistory.expenditure.sum().as("month_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.in(accountIdList)
                )
                .groupBy(formattedDate)
                .orderBy(formattedDate.desc())
                .limit(6)
                .fetch();
    }

    public List<AccountTotalResponseDto> getTotalStatus(Long accountId, AccountSearchCondition condition) {

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m-%d")
        );

        return queryFactory
                .select(new QAccountTotalResponseDto(
                        formattedDate.as("day"),
                        ledgerHistory.expenditure.sum().as("expenditure_sum"),
                        ledgerHistory.income.sum().as("income_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.account.id.eq(accountId),
                        formattedDate.like(condition.getDate()+"%")
                )
                .groupBy(formattedDate)
                .fetch();
    }

    public Page<LedgerHistoryResponseDto> getStatus(Long accountId, AccountSearchCondition condition, Pageable pageable){

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m-%d")
        );

        BooleanBuilder builder = new BooleanBuilder();

        String date = condition.getDate();
        String dateType = condition.getDateType();
        AccountType accountType = condition.getAccountType();
        ExpenditureType expenditureType = condition.getExpenditureType();
        String inputStartDate = condition.getStartDate();
        String inputEndDate = condition.getEndDate();

        if(date != null && dateType == null && inputEndDate == null && inputStartDate == null){
            builder.and(formattedDate.like(condition.getDate()+"%"));
        }

        if(dateType != null) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.withDayOfMonth(1);

            if (dateType.equals("week")) {
                startDate = endDate.minusWeeks(1);
            } else if (dateType.equals("month")) {
                startDate = endDate.minusMonths(1);
            } else if (dateType.equals("3month")) {
                startDate = endDate.minusMonths(3);
            }

            String startDateformat = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDateformat = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            builder.and(formattedDate.between(startDateformat, endDateformat));
        }

        if(dateType == null && inputStartDate != null && inputEndDate != null){
            LocalDate endDate = LocalDate.parse(inputEndDate);
            LocalDate startDate = LocalDate.parse(inputStartDate);

            String startDateformat = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String endDateformat = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            builder.and(formattedDate.between(startDateformat, endDateformat));
        }

        QueryResults<LedgerHistoryResponseDto> results = queryFactory
                .select(new QLedgerHistoryResponseDto(
                        ledgerHistory.id,
                        ledgerHistory.title,
                        ledgerHistory.accountType,
                        ledgerHistory.incomeType,
                        ledgerHistory.expenditureType,
                        ledgerHistory.paymentMethod,
                        ledgerHistory.income,
                        ledgerHistory.expenditure,
                        ledgerHistory.date.stringValue()
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.account.id.eq(accountId),
                        builder,
                        accountTypeEq(condition.getAccountType()),
                        expenditureTypeEq(condition.getExpenditureType()),
                        incomeTypeEq(condition.getIncomeType())
                )
                .orderBy(ledgerHistory.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<LedgerHistoryResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


    public Page<MonthSumResponseDto> getStatistic(Long accountId, AccountSearchCondition condition, Pageable pageable) {
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        QueryResults<MonthSumResponseDto> results = queryFactory
                .select(new QMonthSumResponseDto(
                        formattedDate.as("month"),
                        ledgerHistory.expenditureType,
                        ledgerHistory.expenditure.sum().as("month_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.eq(accountId),
                        formattedDate.eq(condition.getDate())
                )
                .groupBy(formattedDate, ledgerHistory.expenditureType)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MonthSumResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
    
    public MonthSumResponseDto getDifference(Long accountId, AccountSearchCondition condition, LocalDate targetDate, int quarter) {
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        String date = targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        String date_year = targetDate.format(DateTimeFormatter.ofPattern("yyyy"));

        BooleanBuilder builder = new BooleanBuilder();

        if (quarter == 0) {
            builder.and(formattedDate.eq(date));
        }else if (quarter == 1){
            String start_date = date_year+"-01";
            String end_date = date_year+"-03";
            builder.and(formattedDate.between(start_date, end_date));
        }else if (quarter == 2){
            String start_date = date_year+"-04";
            String end_date = date_year+"-06";
            builder.and(formattedDate.between(start_date, end_date));
        }else if (quarter == 3){
            String start_date = date_year+"-07";
            String end_date = date_year+"-09";
            builder.and(formattedDate.between(start_date, end_date));
        }else if (quarter == 4){
            String start_date = date_year+"-10";
            String end_date = date_year+"-12";
            builder.and(formattedDate.between(start_date, end_date));
        }

        return queryFactory
                .select(new QMonthSumResponseDto(
                        formattedDate.as("month"),
                        ledgerHistory.expenditure.sum().as("month_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.eq(accountId),
                        builder
                )
                .fetchOne();
    }

    @Override
    public boolean checkEXPType2(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<Long> accountIdList = getAccountIdList(userId);
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        JPAQuery<LedgerHistory> countQuery = queryFactory
                .select(ledgerHistory)
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                );
        
        if(countQuery.fetchCount() <= 2) return true;
        return false;
    }
    
    @Override
    public boolean checkEXPType3(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        List<Long> accountIdList = getAccountIdList(userId);
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("TRANSPORTATION 교통비");
        if(result <= 120000L) return true;
        return false;
    }
    
    @Override
    public boolean checkEXPType4(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        List<Long> accountIdList = getAccountIdList(userId);
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();
        
        if(result <= 60000L) return true;

        return false;
    }
    
    @Override
    public boolean checkEXPType5(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        List<Long> accountIdList = getAccountIdList(userId);
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        JPAQuery<LedgerHistory> countQuery = queryFactory
                .select(ledgerHistory)
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                );

        if(countQuery.fetchCount() <= 3) return true;
        return false;
    }
    
    @Override
    public boolean checkEXPType7(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        List<Long> accountIdList = getAccountIdList(userId);
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("SAVINGS 저축");
        if(result <= 200000L) return true;
        return false;
    }
    
    @Override
    public boolean checkEXPType8(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        List<Long> accountIdList = getAccountIdList(userId);
        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();
        
        if(result <= 100000L) return true;
        return false;
    }

    @Override
    public boolean checkEXPType9(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        List<Long> accountIdList = getAccountIdList(userId);

        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("HEALTH 건강");
        if(result <= 50000L) return true;
        return false;
    }
    
    @Override
    public boolean checkEXPType10(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        
        List<Long> accountIdList = getAccountIdList(userId);

       
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("FOOD_EXPENSES 식비");
        if(result <= 300000L) return true;
        return false;
    }

    
    @Override
    public boolean checkEXPType11(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        
        List<Long> accountIdList = getAccountIdList(userId);

        
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        JPAQuery<LedgerHistory> countQuery = queryFactory
                .select(ledgerHistory)
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                );
        
        if(countQuery.fetchCount() <= 4) return true;
        return false;
    }
    
    @Override
    public boolean checkEXPType12(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        
        List<Long> accountIdList = getAccountIdList(userId);

       
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum().coalesce(0L))
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();
        
        if(result <= 100000L) return true;
        return false;
    }

    private List<Long> getAccountIdList(Long userId) {
        return queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();
    }

    private BooleanExpression accountTypeEq(AccountType accountType){
        return accountType != null ? ledgerHistory.accountType.eq(accountType) : null;
    }

    private BooleanExpression expenditureTypeEq(ExpenditureType expenditureType){
        return expenditureType != null ? ledgerHistory.expenditureType.eq(expenditureType) : null;
    }

    private BooleanExpression incomeTypeEq(IncomeType incomeType){
        return incomeType != null ? ledgerHistory.incomeType.eq(incomeType) : null;
    }

}
