package com.travelsphere.service;

import com.travelsphere.domain.Statistics;
import com.travelsphere.dto.ExpenseCreateRequestDto;
import com.travelsphere.enums.Currencies;
import com.travelsphere.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final ExchangeRateService exchangeRateService;

    /**
     * 통계를 업데이트한다. (각 도시 카테고리 별)
     *
     * @param expenseCreateRequestDto 지출 등록 요청 DTO
     */
    @Transactional
    public void updateStatistics(ExpenseCreateRequestDto expenseCreateRequestDto) {
        Statistics statistics =
                statisticsRepository.findByCityAndCategory(
                                expenseCreateRequestDto.getCity().getCityName(),
                                expenseCreateRequestDto.getCategory()
                        )
                        .orElseGet(() -> Statistics.builder()
                                .city(expenseCreateRequestDto.getCity().getCityName())
                                .country(expenseCreateRequestDto.getCity().getCountryName())
                                .category(expenseCreateRequestDto.getCategory())
                                .build()
                        );

        Double amount = exchangeRateService.convertCurrency(
                expenseCreateRequestDto.getCurrency(),
                Currencies.KRW,
                expenseCreateRequestDto.getAmount()
        );

        long numbers =
                statistics.getNumberOfExpenses() == null ? 0L : statistics.getNumberOfExpenses();

        statistics.setNumberOfExpenses(numbers + 1);

        double totalAmount =
                statistics.getAverageAmountInKrw() == null ? 0.0 : statistics.getAverageAmountInKrw();

        statistics.setAverageAmountInKrw((totalAmount * numbers + amount) / (numbers + 1));

        statisticsRepository.save(statistics);
    }

}
