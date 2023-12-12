package com.travelsphere.service;

import com.travelsphere.domain.ExchangeRate;
import com.travelsphere.enums.Currencies;
import com.travelsphere.repository.ExchangeRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("환율 서비스 테스트")
class ExchangeRateServiceTest {
    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        exchangeRateService = new ExchangeRateService(exchangeRateRepository);
    }

    ExchangeRate exchangeRate = ExchangeRate.builder()
            .countryName("베트남")
            .currency("VND")
            .toBuy(5.41)
            .time("2021-07-01 11:00:00")
            .basePrice(5.41)
            .currentUnit(100)
            .toSell(5.79)
            .build();

    @Test
    @DisplayName("환율 조회 성공")
    void getExchangeRates() {
        //given
        when(exchangeRateRepository.findByCurrency("VND")).thenReturn(List.of(exchangeRate));
        //when
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findByCurrency("VND");
        //then
        assertThat(exchangeRates.get(0).getCountryName()).isEqualTo("베트남");
    }

    @Test
    @DisplayName("환율 계산 성공")
    void calculateExchangeRates() {
        //given
        Currencies fromCurrency = Currencies.KRW;
        Currencies toCurrency = Currencies.VND;
        when(exchangeRateRepository.findByCurrency("VND")).thenReturn(List.of(exchangeRate));
        //when
        Double value =
                exchangeRateService.convertCurrency(fromCurrency, toCurrency, 1000);
        //then
        assertThat(value).isEqualTo(1.85);
    }
}