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
public class StatisticsExpenseResponseDto {
    private List<StatisticsDto> statistics;

    public static StatisticsExpenseResponseDto from(List<StatisticsDto> statistics) {
        return StatisticsExpenseResponseDto.builder()
                .statistics(statistics)
                .build();
    }
}
