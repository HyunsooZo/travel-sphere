package com.travelsphere.service;

import com.travelsphere.domain.ExchangeRate;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseCreateRequestDto;
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

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@DisplayName("지출 등록 테스트")
class ExpenseCreationServiceTest {

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
    static ExchangeRate exchangeRate = ExchangeRate.builder()
            .countryName("베트남")
            .currency("VND")
            .toBuy(5.41)
            .time("2021-07-01")
            .basePrice(5.41)
            .currentUnit(100)
            .toSell(5.79)
            .build();

    @Test
    @DisplayName("성공")
    void createExpense() {
        // given
        ExpenseCreateRequestDto expense = ExpenseCreateRequestDto.builder()
                .category(ExpenseCategories.ACTIVITY)
                .currency(Currencies.MYR)
                .amount(100.0)
                .date(Date.valueOf("2021-01-01"))
                .city(Cities.KUALA_LUMPUR)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(exchangeRateRepository.findByCurrency("MYR")).thenReturn(List.of(exchangeRate));
        // when
        expenseService.createExpense(1L,expense);

        // then
        verify(expenseRepository, times(1)).save(Mockito.any());
    }

}