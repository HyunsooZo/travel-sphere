package com.travelsphere.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeCalculateResponseDto {
    private Double calculatedAmount;

    public static ExchangeCalculateResponseDto from(Double calculatedAmount){
        return ExchangeCalculateResponseDto.builder()
                .calculatedAmount(calculatedAmount)
                .build();
    }
}
