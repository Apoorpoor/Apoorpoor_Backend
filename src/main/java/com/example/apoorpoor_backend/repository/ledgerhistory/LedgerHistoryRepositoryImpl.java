package com.example.apoorpoor_backend.repository.ledgerhistory;

import com.example.apoorpoor_backend.dto.account.*;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryListResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistoryResponseDto;
import com.example.apoorpoor_backend.dto.ledgerhistory.LedgerHistorySearchCondition;
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
import static com.example.apoorpoor_backend.model.QLedgerHistory.*;

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
     * /user/mypage/status
     * */
    @Override
    public List<TotalSumResponseDto> getMypageStatus(Long userId) {

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        List<TotalSumResponseDto> content = queryFactory
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

        String query =
                "select expenditure_type, sum(expenditure)\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_type = 'EXPENDITURE'\n" +
                        "and account_id in (1, 2, 3)\n" +
                        "group by expenditure_type"
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
        List<Long> accountIdList = getAccountIdList(userId);

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
     * /accounts/{id}/status?
     *  date=YYYY-MM&
        dateType=month&
        account_type=EXPENDITURE&
        expenditure_type=EXPENDITURE_TYPE
        ------------------------------------------------------
        - date
        default : 현재 캘린더의 달
        달력에서 날짜 선택하면 : YYYY-MM-DD

        - dateType
        default(parameter X) : 이번달(1일~마지막일)
        1주일 : week
        1개월 : month
        3개월 : 3month

        - account_type
        전체 : parameter X
        수입 : INCOME
        지출 : EXPENDITURE

        - expenditure_type : 지출별 카테고리 항목
        - income_type : 수입별 카테고리 항목
     * */
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

        // 1. default 현재 캘린더의 달일때 (YYYY-MM)
        if(date != null && dateType == null && inputEndDate == null && inputStartDate == null){
            builder.and(formattedDate.like(condition.getDate()+"%"));
        }

        // 2. dateType 값 1주일, 1개월, 3개월 무조건 현재시간 기준
        if(dateType != null) {
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.withDayOfMonth(1); // default 해당 월의 1일로 초기화

            if (dateType.equals("week")) {
                startDate = endDate.minusWeeks(1);
            } else if (dateType.equals("month")) {
                startDate = endDate.minusMonths(1);
            } else if (dateType.equals("3month")) {
                startDate = endDate.minusMonths(3);
            }

            builder.and(ledgerHistory.date.between(startDate, endDate));
        }

        // 3. 직접입력 기준
        if(dateType == null && inputStartDate != null && inputEndDate != null){
            LocalDate endDate = LocalDate.parse(inputEndDate);
            LocalDate startDate = LocalDate.parse(inputStartDate);

            builder.and(ledgerHistory.date.between(startDate, endDate));
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
                .orderBy(ledgerHistory.date.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

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

        List<LedgerHistoryResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    /*
     * 이번달 상세 지출내역
     * /accounts/{id}/statistics?date=YYYY-MM
     * */
    public Page<MonthSumResponseDto> getStatistic(Long accountId, AccountSearchCondition condition, Pageable pageable) {

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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
        return new PageImpl<>(content, pageable, total);
    }

    /*
     * 이번달, 지난달 지출내역
     * /accounts/{id}/difference/YYYY-MM
     * */
    public MonthSumResponseDto getDifference(Long accountId, AccountSearchCondition condition, LocalDate targetDate, int quarter) {

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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
            // quarter 1, 2, 3, 4
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

    /* CONDOLENCE_EXPENSE 경조사비 : 2번 이하*/
    @Override
    public boolean checkEXPType2(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

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
        if(countQuery.fetchCount() <= 2) return true;
        return false;
    }

    /* TRANSPORTATION 교통비 : 12만원 이하 */
    @Override
    public boolean checkEXPType3(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

    /* COMMUNICATION_EXPENSES 통신비 : 6만원 이하 */
    @Override
    public boolean checkEXPType4(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        log.info("COMMUNICATION_EXPENSES 통신비");
        if(result <= 60000L) return true;

        return false;
    }

    /* INSURANCE 보험 : 3개 이하*/
    @Override
    public boolean checkEXPType5(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

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
        if(countQuery.fetchCount() <= 3) return true;
        return false;
    }

    /* SAVINGS 저축 : 20 / 30 / 50 만원  */
    @Override
    public boolean checkEXPType7(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

    /* CULTURE 문화: 10만원  */
    @Override
    public boolean checkEXPType8(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        log.info("CULTURE 문화");
        if(result <= 100000L) return true;
        return false;
    }

    /* HEALTH 건강: 5만원 */
    @Override
    public boolean checkEXPType9(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

    /* FOOD_EXPENSES 식비 : 30만원 */
    @Override
    public boolean checkEXPType10(ExpenditureType expenditureType, Long userId) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

    /* SHOPPING 쇼핑 : 4번 */
    @Override
    public boolean checkEXPType11(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

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
        if(countQuery.fetchCount() <= 4) return true;
        return false;
    }

    /* LEISURE_ACTIVITIES 여가생활 : 10만원  */
    @Override
    public boolean checkEXPType12(ExpenditureType expenditureType, Long userId) {

        LocalDate yesterday = LocalDate.now().minusDays(1);
        String previousMonth = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        // user_id로 생성된 account_id 찾기
        List<Long> accountIdList = getAccountIdList(userId);

        // date_format(date, '%Y-%m') querydsl로 바꾸기
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

        log.info("LEISURE_ACTIVITIES 여가생활");
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
