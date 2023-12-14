package com.travelsphere.service;

import com.travelsphere.domain.ExchangeRate;
import com.travelsphere.domain.Expense;
import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.dto.ExpenseModificationRequestDto;
import com.travelsphere.enums.*;
import com.travelsphere.repository.ExchangeRateRepository;
import com.travelsphere.repository.ExpenseRepository;
import com.travelsphere.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("지출 수정 테스트")
class ExpenseModificationServiceTest {

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

    static ExchangeRate exchangeRate = ExchangeRate.builder()
            .countryName("베트남")
            .currency("VND")
            .toBuy(5.41)
            .time("2021-07-01 11:00:00")
            .basePrice(5.41)
            .currentUnit(100)
            .toSell(5.79)
            .build();

    ExchangeRate exchangeRate2 = ExchangeRate.builder()
            .countryName("말레이시아")
            .currency("MYR")
            .toBuy(8.0)
            .time("2021-07-01 11:00:00")
            .basePrice(8.0)
            .currentUnit(100)
            .toSell(8.0)
            .build();
    @Test
    @DisplayName("성공")
    void createExpense() {
        //given
        ExpenseModificationRequestDto expenseModificationRequestDto = ExpenseModificationRequestDto.builder()
                .category(ExpenseCategories.FOOD)
                .currency(Currencies.MYR)
                .amount(10000.0)
                .date(Date.valueOf("2023-07-01"))
                .city(Cities.KUALA_LUMPUR)
                .build();

        when(expenseRepository.findById(expense.getId())).thenReturn(Optional.of(expense));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(exchangeRateRepository.findByCurrency("VND")).thenReturn(List.of(exchangeRate));
        when(exchangeRateRepository.findByCurrency("MYR")).thenReturn(List.of(exchangeRate2));
        //when
        expenseService.updateExpense(user.getId(), expense.getId(), expenseModificationRequestDto);
        //then
        assertThat(expense.getCategory()).isEqualTo(expenseModificationRequestDto.getCategory());
        assertThat(expense.getCurrency()).isEqualTo(expenseModificationRequestDto.getCurrency().getCurrencyName());
        assertThat(expense.getAmount()).isEqualTo(expenseModificationRequestDto.getAmount());
    }

}