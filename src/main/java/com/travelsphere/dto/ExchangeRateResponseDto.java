package com.travelsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeRateResponseDto {
    private List<ExchangeRateDto> exchangeRates;

    /**
     * ExchangeRateResponseDto 객체 생성
     * @param exchangeRates 환율 정보
     * @return ExchangeRateResponseDto 객체
     */
    public static ExchangeRateResponseDto from(List<ExchangeRateDto> exchangeRates){
        return ExchangeRateResponseDto.builder()
                .exchangeRates(exchangeRates)
                .build();
    }
}
