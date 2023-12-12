package com.travelsphere.dto;

import com.travelsphere.domain.ExchangeRate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateDto {
    private String currency;
    private String countryName;
    private Double toBuy;
    private Double toSell;
    private Double basePrice;
    private Integer currentUnit;
    private String time;

    public static ExchangeRateDto from(ExchangeRate exchangeRate){
        return ExchangeRateDto.builder()
                .currency(exchangeRate.getCurrency())
                .countryName(exchangeRate.getCountryName())
                .toBuy(exchangeRate.getToBuy())
                .toSell(exchangeRate.getToSell())
                .basePrice(exchangeRate.getBasePrice())
                .currentUnit(exchangeRate.getCurrentUnit())
                .time(exchangeRate.getTime())
                .build();
    }
}
