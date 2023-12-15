package com.travelsphere.service;

import com.travelsphere.domain.Expense;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseDto;
import com.travelsphere.enums.*;
import com.travelsphere.repository.ExchangeRateRepository;
import com.travelsphere.repository.ExpenseRepository;
import com.travelsphere.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@DisplayName("지출 삭제 테스트")
class ExpenseDeleteServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private UserRepository userRepository;

    private ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    private ExpenseService expenseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        exchangeRateService = new ExchangeRateService(exchangeRateRepository);
        expenseService = new ExpenseService(expenseRepository, userRepository, exchangeRateService);
    }

    static User user = User.builder()
            .id(1L)
            .email("test@test.com")
            .phone("01012345678")
            .password("encoded")
            .userStatus(UserStatus.ACTIVE)
            .userRole(UserRole.ROLE_USER)
            .build();
    static Expense expense = Expense.builder()
            .id(1L)
            .user(user)
            .category(ExpenseCategories.ACTIVITY)
            .currency(Currencies.VND.getCurrencyName())
            .amount(10000.0)
            .amountOfKrw(1.0)
            .date(Date.valueOf("2021-07-01"))
            .city(Cities.HANOI.getCityName())
            .country(Countries.VIETNAM.getCountryName())
            .build();

    @Test
    @DisplayName("성공")
    void success() {
        //given
        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        expenseService.deleteExpense(1L, 1L);
        //then
        verify(expenseRepository, times(1)).delete(expense);
    }
}