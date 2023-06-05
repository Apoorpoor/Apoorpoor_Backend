package com.example.apoorpoor_backend.repository.dsl;

import com.example.apoorpoor_backend.dto.*;
import com.example.apoorpoor_backend.model.enumType.AccountType;
import com.example.apoorpoor_backend.model.enumType.ExpenditureType;
import com.example.apoorpoor_backend.repository.LedgerHistoryRepositoryCustom;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import static com.example.apoorpoor_backend.model.QAccount.account;
import static com.example.apoorpoor_backend.model.QLedgerHistory.*;
import static com.querydsl.core.group.GroupBy.groupBy;

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
    public List<MyPageResponseDto> getMypageStatus(MyPageSearchCondition condition, Long userId) {

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

        List<MyPageResponseDto> content = queryFactory
                .select(new QMyPageResponseDto(
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
    public List<MyPageResponseDto> getRecentStatus(Long userId) {

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

        List<MyPageResponseDto> content = queryFactory
                .select(new QMyPageResponseDto(
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
     * /accounts/{id}/status?date=YYYY-MM
     * */
    public List<LedgerHistoryListResponseDto> search3(LedgerHistorySearchCondition condition) {
        String query =
                "select DATE_FORMAT(date, '%Y-%m-%d') as day,\n" +
                        "       sum(expenditure) as expenditure_sum,\n" +
                        "       sum(income) as income_sum\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_id = 1\n" +
                        "and date like '2022-05%'\n" +
                        "group by day";
        return null;
    }

    /*
     * 해당 날짜의 상세 지출/소비 내역
     * /accounts/{id}/status?date=YYYY-MM-DD
     * */
    public List<LedgerHistoryListResponseDto> search4(LedgerHistorySearchCondition condition) {
        String query =
                "select *\n" +
                        "from ledger_history\n" +
                        "where 1=1\n" +
                        "and account_id = 1\n" +
                        "and date = '2022-05-01'";
        return null;
    }

    /*
     * 이번달 상세 지출내역
     * /accounts/{id}/statistics?date=YYYY-MM
     * */
    public List<LedgerHistoryListResponseDto> search6(LedgerHistorySearchCondition condition) {
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
        return null;
    }

    /*
     * 이번달, 지난달 지출내역
     * /accounts/{id}/recentStatus
     * */
    public List<LedgerHistoryListResponseDto> search5(LedgerHistorySearchCondition condition) {
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
        return null;
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

}
