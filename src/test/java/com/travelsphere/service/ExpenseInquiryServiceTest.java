package com.travelsphere.service;

import com.travelsphere.domain.User;
import com.travelsphere.dto.ExpenseDto;
import com.travelsphere.enums.ExpenseCategories;
import com.travelsphere.enums.UserRole;
import com.travelsphere.enums.UserStatus;
import com.travelsphere.repository.ExchangeRateRepository;
import com.travelsphere.repository.ExpenseRepository;
import com.travelsphere.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("지출 조회 테스트")
class ExpenseInquiryServiceTest {

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

    static PageRequest pageRequest = PageRequest.of(0, 10);

    static ExpenseDto expenseDto = ExpenseDto.builder()
            .id(1L)
            .category(ExpenseCategories.ACTIVITY)
            .currency("KRW")
            .amount(10000.0)
            .amountOfKrw(10000.0)
            .date(Date.valueOf("2021-07-01"))
            .city("하노이")
            .country("베트남")
            .userId(user.getId())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    static ExpenseDto expenseDto2 = ExpenseDto.builder()
            .id(2L)
            .category(ExpenseCategories.ACTIVITY)
            .currency("KRW")
            .amount(10000.0)
            .amountOfKrw(10000.0)
            .date(Date.valueOf("2021-07-01"))
            .city("하노이")
            .country("베트남")
            .userId(user.getId())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    static List<ExpenseDto> expenseDtoList = List.of(expenseDto, expenseDto2);
    static Page<ExpenseDto> expenses = new PageImpl<>(expenseDtoList, pageRequest,
            expenseDtoList.size());

    @Test
    @DisplayName("성공")
    void success() {
        // given
        when(expenseRepository.findAllByUserId(user, pageRequest))
                .thenReturn(expenses);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // when
        Page<ExpenseDto> result = expenseService.getExpenses(1L, pageRequest);
        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(0).getId()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getAmountOfKrw()).isEqualTo(10000.0);
    }
}