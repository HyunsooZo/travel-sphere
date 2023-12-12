package com.travelsphere.service;

import com.travelsphere.domain.ExchangeRate;
import com.travelsphere.dto.ExchangeRateDto;
import com.travelsphere.enums.Countries;
import com.travelsphere.enums.Currencies;
import com.travelsphere.repository.ExchangeRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    /**
     * 환율 정보 조회
     *
     * @param countryName 국가명
     * @return 환율 정보
     */
    @Cacheable(value = "exchangeRate", key = "'rate_of:' + #countryName")
    @Transactional
    public List<ExchangeRateDto> getExchangeRates(Countries countryName) {
        List<ExchangeRate> list;
        if (countryName.equals(Countries.ALL)) {
            list = exchangeRateRepository.findAll();
        } else {
            list = exchangeRateRepository.findByCurrency(countryName.getCurrency());
        }
        return list.stream()
                .map(ExchangeRateDto::from)
                .collect(Collectors.toList());
    }

    /**
     * 환율 계산
     * @param fromCurrency 입력된 화폐
     * @param toCurrency 출력할 화폐
     * @param amount 금액
     * @return 환율 계산 결과
     */
    public Double convertCurrency(Currencies fromCurrency,
                                  Currencies toCurrency,
                                  double amount) {


        ExchangeRate exchangeRate =
                fromCurrency.equals(Currencies.KRW) ?
                        exchangeRateRepository.findByCurrency(toCurrency.getCurrencyName()).get(0) :
                        exchangeRateRepository.findByCurrency(fromCurrency.getCurrencyName()).get(0);

        boolean toKrw = toCurrency.equals(Currencies.KRW);

        double currentUnit = exchangeRate.getCurrentUnit();

        double basePrice = exchangeRate.getBasePrice();

        while (currentUnit > 1) {
            basePrice = basePrice * 10;
            currentUnit = currentUnit / 10;
        }

        return toKrw ? amount * basePrice : amount / basePrice;
    }
}
