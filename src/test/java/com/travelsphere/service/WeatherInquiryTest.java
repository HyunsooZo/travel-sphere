package com.travelsphere.service;

import com.travelsphere.domain.Weather;
import com.travelsphere.dto.WeatherDto;
import com.travelsphere.repository.WeatherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("날씨 조회 테스트")
class WeatherInquiryTest {

    @Mock
    private WeatherRepository weatherRepository;

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        weatherService = new WeatherService(weatherRepository);
    }

    Weather weather1 = Weather.builder()
            .cityName("Bangkok")
            .countryName("Thailand")
            .weather("Clouds")
            .currentTemperature("30.0")
            .maxTemperature("30.0")
            .minTemperature("30.0")
            .feelsLike("30.0")
            .humidity(30.0)
            .build();

    Weather weather2 = Weather.builder()
            .cityName("Seoul")
            .countryName("Korea")
            .weather("Clouds")
            .currentTemperature("30.0")
            .maxTemperature("30.0")
            .minTemperature("30.0")
            .feelsLike("30.0")
            .humidity(30.0)
            .build();

    @Test
    @DisplayName("성공 (개별도시)")
    void getCityWeather() {
        // given
        String cityName = "Bangkok";

        Mockito.when(weatherRepository.findByCityName(cityName))
                .thenReturn(Collections.singletonList(weather1));
        // when
        List<WeatherDto> weatherServiceCityWeather = weatherService.getCityWeather(cityName);

        // then
        Assertions.assertThat(weatherServiceCityWeather.get(0).getCityName())
                .isEqualTo("Bangkok");
    }

    @Test
    @DisplayName("성공 (전체도시)")
    void getAllCityWeather() {
        // given
        String cityName = "All";

        Mockito.when(weatherRepository.findAll())
                .thenReturn(Arrays.asList(weather1, weather2));
        // when
        List<WeatherDto> weatherServiceCityWeather = weatherService.getCityWeather(cityName);

        // then
        Assertions.assertThat(weatherServiceCityWeather.get(0).getCityName())
                .isEqualTo("Bangkok");
        Assertions.assertThat(weatherServiceCityWeather.get(1).getCityName())
                .isEqualTo("Seoul");
    }

}