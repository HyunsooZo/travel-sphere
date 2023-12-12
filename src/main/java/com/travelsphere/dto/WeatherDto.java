package com.travelsphere.dto;

import com.travelsphere.domain.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherDto {

    private String cityName;

    private String countryName;

    private String weather;

    private String currentTemperature;

    private String minTemperature;

    private String maxTemperature;

    private Double humidity;

    private String feelsLike;

    public static WeatherDto from(Weather weather) {
        return WeatherDto.builder()
                .cityName(weather.getCityName())
                .countryName(weather.getCountryName())
                .weather(weather.getWeather())
                .currentTemperature(weather.getCurrentTemperature())
                .minTemperature(weather.getMinTemperature())
                .maxTemperature(weather.getMaxTemperature())
                .humidity(weather.getHumidity())
                .feelsLike(weather.getFeelsLike())
                .build();
    }
}
