package com.travelsphere.dto;

import com.travelsphere.domain.Statistics;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDto {
    private String country;
    private String city;
    private Long numberOfExpenses;
    private String category;
    private Double averageAmountInKrw;

    public static StatisticsDto from(Statistics statistics) {
        return StatisticsDto.builder()
                .country(statistics.getCountry())
                .city(statistics.getCity())
                .numberOfExpenses(statistics.getNumberOfExpenses())
                .category(statistics.getCategory().getCategoryName())
                .averageAmountInKrw(statistics.getAverageAmountInKrw())
                .build();
    }
}
