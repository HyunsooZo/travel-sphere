package com.travelsphere.service;

import com.travelsphere.domain.ExchangeRate;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.repository.ExchangeRateRepository;
import com.travelsphere.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static com.travelsphere.enums.Cities.BANGKOK;
import static com.travelsphere.enums.Cities.KUALA_LUMPUR;
import static com.travelsphere.enums.Currencies.KRW;
import static com.travelsphere.enums.Currencies.MYR;
import static com.travelsphere.enums.ExpenseCategories.ETC;
import static org.mockito.Mockito.*;

@DisplayName("도시별 평균 소비금액 저장 테스트")
class StatisticsCitiesServiceTest {
    @Mock
    private StatisticsRepository statisticsRepository;

    private StatisticsService statisticsService;

    @Mock
    private ExchangeRateService exchangeRateService;

    @Mock
    private ExchangeRateRepository exchangeRateRepository;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        statisticsService = new StatisticsService(
                statisticsRepository, exchangeRateService
        );
    }

    ExpenseCreateRequestDto expenseCreateRequestDto = ExpenseCreateRequestDto.builder()
            .city(BANGKOK)
            .category(ETC)
            .currency(MYR)
            .amount(1000.0)
            .build();

    ExchangeRate exchangeRate1 = ExchangeRate.builder()
            .countryName("베트남")
            .currency("MYR")
            .toBuy(5.41)
            .time("2021-07-01 11:00:00")
            .basePrice(5.41)
            .currentUnit(100)
            .toSell(5.79)
            .build();

    ExchangeRate exchangeRate2 = ExchangeRate.builder()
            .countryName("베트남")
            .currency("VND")
            .toBuy(5.41)
            .time("2021-07-01 11:00:00")
            .basePrice(5.41)
            .currentUnit(100)
            .toSell(5.79)
            .build();

    @Test
    @DisplayName("성공")
    void getStatisticsByCity() {
        // given
        when(statisticsRepository.findByCityAndCategory(KUALA_LUMPUR.getCityName(), ETC))
                .thenReturn(null);
        when(exchangeRateService.convertCurrency(MYR, KRW, 1000.0))
                .thenReturn(5000.0);
        when(statisticsRepository.save(Mockito.any()))
                .thenReturn(null);
        when(exchangeRateRepository.findByCurrency(MYR.getCurrencyName()))
                .thenReturn(List.of(exchangeRate1));
        when(exchangeRateRepository.findByCurrency(KRW.getCurrencyName()))
                .thenReturn(List.of(exchangeRate2));
        when(exchangeRateService.convertCurrency(MYR, KRW, 1000.0))
                .thenReturn(5000.0);

        // when
        statisticsService.updateStatistics(expenseCreateRequestDto);
        // then
        verify(statisticsRepository, times(1))
                .save(Mockito.any());
    }
}