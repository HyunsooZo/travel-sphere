package com.travelsphere.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travelsphere.domain.Expense;
import com.travelsphere.domain.QExpense;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ExpenseQRepositoryImpl implements ExpenseQRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 사용자의 지출 내역을 조회한다.
     *
     * @param user     사용자
     * @param pageable 페이지 정보
     * @return 지출 내역
     */
    @Override
    public Page<ExpenseDto> findAllByUserId(User user, Pageable pageable) {
        QExpense expense = QExpense.expense;

        BooleanExpression filterByUserId = expense.user.eq(user);

        long totalCount = queryFactory
                .selectFrom(expense)
                .where(filterByUserId)
                .fetchCount();

        JPAQuery<Expense> query = queryFactory
                .selectFrom(expense)
                .where(filterByUserId)
                .orderBy(expense.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        List<Expense> result = query.fetch();

        return new PageImpl<>(
                result.stream().map(ExpenseDto::from).collect(Collectors.toList()),
                pageable, totalCount
        );
    }
}
