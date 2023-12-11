package com.travelsphere.service;

import com.travelsphere.domain.Weather;
import com.travelsphere.dto.WeatherDto;
import com.travelsphere.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    /**
     * 특정도시 날씨정보 조회
     *
     * @param cityName 도시이름
     * @return WeatherDto
     */
    @Cacheable(value = "weather", key = "'weather_of:' + #cityName")
    @Transactional(readOnly = true)
    public List<WeatherDto> getCityWeather(String cityName) {

        List<Weather> weathers;
        if (cityName.equals("All")) {
            weathers = weatherRepository.findAll();
        } else {
            weathers = weatherRepository.findByCityName(cityName);
        }

        return weathers.stream()
                .map(WeatherDto::from)
                .collect(Collectors.toList());
    }
}
