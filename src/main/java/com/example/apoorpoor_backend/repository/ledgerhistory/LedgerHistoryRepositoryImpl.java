package com.example.apoorpoor_backend.repository.ledgerhistory;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryListResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistorySearchCondition;
import com.example.apoorpoor_backend.dto.ledgerhistory.QLedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.user.MyPageSearchCondition;
import com.example.apoorpoor_backend.model.LedgerHistory;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.model.enumType.IncomeType;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static com.example.apoorpoor_backend.model.QAccount.account;
import static com.example.apoorpoor_backend.model.QLedgerHistory.*;
import static com.querydsl.core.group.GroupBy.groupBy;

@Slf4j
public class LedgerHistoryRepositoryImpl implements LedgerHistoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LedgerHistoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<LedgerHistoryListResponseDto> search(LedgerHistorySearchCondition condition) {
        return null;
    }

    /*
     * 마이페이지 - 소비성향 육각형 그래프
     * /users/mypage?date=2023-04
     * */
    @Override
    public List<MonthSumResponseDto> getMypageStatus(MyPageSearchCondition condition, Long userId) {

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<MonthSumResponseDto> content = queryFactory
                .select(new QMonthSumResponseDto(
                        formattedDate.as("month"),
                        ledgerHistory.expenditureType,
                        ledgerHistory.expenditure.sum().as("month_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(condition.getDate())
                )
                .groupBy(formattedDate, ledgerHistory.expenditureType)
                .fetch();

        String query =
                "select DATE_FORMAT(date, '%Y-%m') as month,\n" +
                        "       expenditure_type,\n" +
                        "       sum(expenditure) as month_sum\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_type = 'EXPENDITURE'\n" +
                        "and account_id in (1, 2, 3)\n" +
                        "and date_format(date, '%Y-%m') = '2022-05'\n" +
                        "group by month, expenditure_type"
                ;
        return content;
    }

    /*
     * 마이페이지 - 최근 6개월 소비근황
     * /users/mypage/recentStatus
     * */
    @Override
    public List<MonthSumResponseDto> getRecentStatus(Long userId) {

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<MonthSumResponseDto> content = queryFactory
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

        String query =
                "select DATE_FORMAT(date, '%Y-%m') as month,\n" +
                        "       sum(expenditure) as month_sum\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_type = 'EXPENDITURE'\n" +
                        "and account_id in (1, 2, 3)\n" +
                        "group by month\n" +
                        "order by month desc \n" +
                        "limit 6";
        return content;
    }

    /*
     * 가계부 - 월별/날짜별 총 수입/지출 금액
     * /accounts/{id}/totalStatus?date=YYYY-MM
     * */
    public List<AccountTotalResponseDto> getTotalStatus(Long accountId, AccountSearchCondition condition) {

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m-%d")
        );

        List<AccountTotalResponseDto> content = queryFactory
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

        String query =
                "select DATE_FORMAT(date, '%Y-%m-%d') as day,\n" +
                        "       sum(expenditure) as expenditure_sum,\n" +
                        "       sum(income) as income_sum\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_id = 1\n" +
                        "and date like '2022-05%'\n" +
                        "group by day";
        return content;
    }


    /*
     * 수입 지출별 카테고리 필터링
     * /accounts/{id}/status?date=YYYY-MM&accunt_type='EXPENDITURE'&expenditure_type='UTILITY_BILL'
     */
    public List<LedgerHistoryListResponseDto> search7(LedgerHistorySearchCondition condition) {
        String query =
                "select *\n" +
                        "    from ledger_history\n" +
                        "    where 1=1\n" +
                        "    and account_id = 1\n" +
                        "    and date like '2022-05%'\n" +
                        "    and account_type = 'EXPENDITURE'\n" +
                        "    and expenditure_type = 'UTILITY_BILL'\n" +
                        "order by date desc "
                ;
        return null;
    }



    /*
     * 해당 날짜의 상세 지출/소비 내역
     * /accounts/{id}/status?date=YYYY-MM-DD
     * * 수입 지출별 카테고리 필터링
     * /accounts/{id}/status?date=YYYY-MM&account_type='EXPENDITURE'&expenditure_type='UTILITY_BILL'
     * */
    public List<LedgerHistoryResponseDto> getStatus(Long accountId, AccountSearchCondition condition){

        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m-%d")
        );

        List<LedgerHistoryResponseDto> content = queryFactory
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
                        formattedDate.like(condition.getDate()+"%"),
                        accountTypeEq(condition.getAccountType()),
                        expenditureTypeEq(condition.getExpenditureType()),
                        incomeTypeEq(condition.getIncomeType())
                )
                .orderBy(formattedDate.desc())
                .fetch();

        String query =
                "select *\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_id = 1\n" +
                        "and date = '2022-05-01'";

        String query2 =
                "select *\n" +
                        "    from ledger_history\n" +
                        "    where 1=1\n" +
                        "    and account_id = 1\n" +
                        "    and date like '2022-05%'\n" +
                        "    and account_type = 'EXPENDITURE'\n" +
                        "    and expenditure_type = 'UTILITY_BILL'\n" +
                        "order by date desc "
                ;
        return content;
    }

    /*
     * 이번달 상세 지출내역
     * /accounts/{id}/statistics?date=YYYY-MM
     * */
    public List<MonthSumResponseDto> getStatistic(Long accountId, AccountSearchCondition condition) {

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<MonthSumResponseDto> content = queryFactory
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
                .fetch();


        String query =
                "select DATE_FORMAT(date, '%Y-%m') as month,\n" +
                        "       expenditure_type,\n" +
                        "       sum(expenditure) as month_sum\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_type = 'EXPENDITURE'\n" +
                        "and account_id = 1\n" +
                        "and date_format(date, '%Y-%m') = '2022-05'\n" +
                        "group by month, expenditure_type"
                ;
        return content;
    }

    /*
     * 이번달, 지난달 지출내역
     * /accounts/{id}/difference
     * */
    public List<MonthSumResponseDto> getDifference(Long accountId) {

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        List<MonthSumResponseDto> content = queryFactory
                .select(new QMonthSumResponseDto(
                        formattedDate.as("month"),
                        ledgerHistory.expenditure.sum().as("month_sum")
                ))
                .from(ledgerHistory)
                .where(
                        ledgerHistory.accountType.eq(AccountType.EXPENDITURE),
                        ledgerHistory.account.id.eq(accountId)
                )
                .groupBy(formattedDate)
                .orderBy(formattedDate.desc())
                .limit(2)
                .fetch();

        String query =
                "select DATE_FORMAT(date, '%Y-%m') as month,\n" +
                        "                               sum(expenditure) as month_sum\n" +
                        "                        from ledger_history\n" +
                        "                        where 1=1\n" +
                        "                        and account_type = 'EXPENDITURE'\n" +
                        "                        and account_id = 1\n" +
                        "                        group by month\n" +
                        "                        order by month desc\n" +
                        "                        limit 2"
                ;
        return content;
    }

    /* UTILITY_BILL 월세+관리비+공과금 : 1번 */
    @Override
    public boolean checkEXPType1(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        String query = "select count(*)\n" +
                "from ledger_history\n" +
                "where 1=1\n" +
                "and account_type = 'EXPENDITURE'\n" +
                "and expenditure_type = 'UTILITY_BILL'\n" +
                "and account_id in (accountlist)\n" +
                "and date like '이전달%';";

        log.info("UTILITY_BILL 월세+관리비+공과금");
        if(countQuery.fetchCount() >= 1) return true;
        return false;
    }

    /* CONDOLENCE_EXPENSE 경조사비 : 2번 */
    @Override
    public boolean checkEXPType2(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        String query = "select count(*)\n" +
                "from ledger_history\n" +
                "where 1=1\n" +
                "and account_type = 'EXPENDITURE'\n" +
                "and expenditure_type = 'UTILITY_BILL'\n" +
                "and account_id in (accountlist)\n" +
                "and date like '이전달%';";

        log.info("CONDOLENCE_EXPENSE 경조사비");
        if(countQuery.fetchCount() >= 2) return true;
        return false;
    }

    /* TRANSPORTATION 교통비 : 12만원 이상 */
    @Override
    public boolean checkEXPType3(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("TRANSPORTATION 교통비");
        if(result >= 120000L) return true;
        return false;
    }

    /* COMMUNICATION_EXPENSES 통신비 : 6만원 이상 */
    @Override
    public boolean checkEXPType4(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("COMMUNICATION_EXPENSES 통신비");
        if(result >= 60000L) return true;

        return false;
    }

    /* INSURANCE 보험 : 3개 */
    @Override
    public boolean checkEXPType5(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        String query = "select count(*)\n" +
                "from ledger_history\n" +
                "where 1=1\n" +
                "and account_type = 'EXPENDITURE'\n" +
                "and expenditure_type = 'UTILITY_BILL'\n" +
                "and account_id in (accountlist)\n" +
                "and date like '이전달%';";

        log.info("INSURANCE 보험");
        if(countQuery.fetchCount() >= 3) return true;
        return false;
    }

    /* EDUCATION 교육 : 15만원 */
    @Override
    public boolean checkEXPType6(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("EDUCATION 교육");
        if(result >= 150000L) return true;
        return false;
    }

    /* SAVINGS 저축 : 20 / 30 / 50 만원  */
    @Override
    public boolean checkEXPType7(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("SAVINGS 저축");
        if(result >= 200000L) return true;
        return false;
    }

    /* CULTURE 문화: 10만원  */
    @Override
    public boolean checkEXPType8(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("CULTURE 문화");
        if(result >= 100000L) return true;
        return false;
    }

    /* HEALTH 건강: 5만원 */
    @Override
    public boolean checkEXPType9(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("HEALTH 건강");
        if(result >= 50000L) return true;
        return false;
    }

    /* FOOD_EXPENSES 식비 : 30만원 */
    @Override
    public boolean checkEXPType10(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("FOOD_EXPENSES 식비");
        if(result >= 300000L) return true;
        return false;
    }

    /* SHOPPING 쇼핑 : 4번 */
    @Override
    public boolean checkEXPType11(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        log.info("SHOPPING 쇼핑");
        if(countQuery.fetchCount() >= 4) return true;
        return false;
    }

    /* LEISURE_ACTIVITIES 여가생활 : 10만원  */
    @Override
    public boolean checkEXPType12(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = queryFactory
                .select(account.id)
                .from(account)
                .where(account.user.id.eq(userId))
                .fetch();

        // date_format(date, '%Y-%m') querydsl로 바꾸기
        StringTemplate formattedDate = Expressions.stringTemplate(
                "DATE_FORMAT({0}, {1})"
                ,ledgerHistory.date
                ,ConstantImpl.create("%Y-%m")
        );

        Long result = queryFactory
                .select(ledgerHistory.expenditure.sum())
                .from(ledgerHistory)
                .where(
                        accountTypeEq(AccountType.EXPENDITURE),
                        expenditureTypeEq(expenditureType),
                        ledgerHistory.account.id.in(accountIdList),
                        formattedDate.eq(previousMonth)
                )
                .fetchOne();

        log.info("LEISURE_ACTIVITIES 여가생활");
        if(result >= 100000L) return true;
        return false;
    }


    private BooleanExpression accountTypeEq(AccountType accountType){
        return accountType != null ? ledgerHistory.accountType.eq(accountType) :  null;
    }

    private BooleanExpression expenditureTypeEq(ExpenditureType expenditureType){
        return expenditureType != null ? ledgerHistory.expenditureType.eq(expenditureType) : null;
    }

    private BooleanExpression incomeTypeEq(IncomeType incomeType){
        return incomeType != null ? ledgerHistory.incomeType.eq(incomeType) : null;
    }

}
