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
public class WeatherResponseDto {

    private List<WeatherDto> weathers;

    public static WeatherResponseDto from(List<WeatherDto> weathers) {
        return WeatherResponseDto.builder()
                .weathers(weathers)
                .build();
    }
}
