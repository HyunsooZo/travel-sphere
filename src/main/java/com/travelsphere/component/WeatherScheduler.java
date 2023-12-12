package com.travelsphere.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsphere.domain.Weather;
import com.travelsphere.enums.Cities;
import com.travelsphere.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Component
public class WeatherScheduler {
    private final WeatherRepository weatherRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    @Value("${external-api.weather.url}")
    private String weatherApiUrl;

    @Value("${external-api.weather.key}")
    private String weatherApiKey;

    /**
     * 매 시간마다 날씨정보를 가져와서 DB에 저장
     */
    @Scheduled(cron = "0 0 */3 * * *")
    @Transactional
    public void getWeatherInfo() {
        Arrays.stream(Cities.values()).forEach(city -> {
            if(city == Cities.ALL) {
                return;
            }

            String url = String.format(weatherApiUrl, city.getCityName(), weatherApiKey);

            String response = callApi(url);

            Weather weather = jsonParser(
                    city.getCountryName(), city.getCityName(), response
            );

            if (weather != null) {
                weatherRepository.save(weather);
                log.info("weather info save success : {}", weather.getCityName());
                evictCityWeatherCache(city.getCityName());
            }
        });
        evictCityWeatherCache(Cities.ALL.getCityName());
    }

    /**
     * 외부 API로부터 받은 JSON 데이터를 파싱
     *
     * @param city     도시명
     * @param response 외부 API로부터 받은 JSON 데이터
     * @return Weather
     */
    private Weather jsonParser(String country, String city, String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            String weather = jsonNode.get("weather").get(0).get("main").asText();
            double currentTemperature = jsonNode.get("main").get("temp").asDouble();
            double minTemperature = jsonNode.get("main").get("temp_min").asDouble();
            double maxTemperature = jsonNode.get("main").get("temp_max").asDouble();
            Double humidity = jsonNode.get("main").get("humidity").asDouble();
            double feelsLike = jsonNode.get("main").get("feels_like").asDouble();

            return Weather.builder()
                    .cityName(city)
                    .countryName(country)
                    .weather(weather)
                    .currentTemperature(convertToCelsius(currentTemperature))
                    .minTemperature(convertToCelsius(minTemperature))
                    .maxTemperature(convertToCelsius(maxTemperature))
                    .humidity(humidity)
                    .feelsLike(convertToCelsius(feelsLike))
                    .build();

        } catch (Exception e) {
            log.error("json parsing error : {}", e.getMessage());
        }
        return null;
    }

    /**
     * 켈빈 온도를 섭씨 온도로 변환
     *
     * @param temperatureKelvin 켈빈 온도
     * @return 섭씨 온도
     */
    private String convertToCelsius(double temperatureKelvin) {
        return String.format("%.1f", temperatureKelvin - 273.15);
    }

    /**
     * 외부 API 호출
     *
     * @param url 외부 API URL
     * @return 외부 API로부터 받은 JSON 데이터
     */
    private String callApi(String url) {
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 특정도시의 날씨정보 캐시 삭제
     *
     * @param cityName 도시이름
     */
    public void evictCityWeatherCache(String cityName) {
        Cache weatherCache = cacheManager.getCache("weather");
        if (weatherCache != null) {
            weatherCache.evict("weather_of:" + cityName);
        }
    }
}