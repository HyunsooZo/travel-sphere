package com.travelsphere.component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelsphere.domain.ExchangeRate;
import com.travelsphere.enums.Countries;
import com.travelsphere.repository.ExchangeRateRepository;
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
public class ExchangeRateScheduler {
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final CacheManager cacheManager;

    @Value("${external-api.currency.url}")
    private String currencyApiUrl;

    /**
     * 1일 8회 환율정보를 가져와서 DB에 저장
     */
    @Scheduled(cron = "0 0 */3 * * *")
    @Transactional
    public void getExchangeRateInfo() {
        Arrays.stream(Countries.values()).forEach(country -> {

            if (country == Countries.ALL) {
                return;
            }

            String url = String.format(currencyApiUrl, country.getCurrency());

            String response = callApi(url);

            ExchangeRate exchangeRate = jsonParser(country, response);

            if (exchangeRate != null) {
                exchangeRateRepository.save(exchangeRate);
                log.info("exchange rate info save success : {}", exchangeRate.getCountryName());
                evictExchangeRateCache(country.getCountryName());
            }

        });
        log.info("exchange rate info save finished");
        evictExchangeRateCache(Countries.ALL.getCountryName());

    }

    /**
     * JSON 데이터 파싱
     *
     * @param country  국가이름
     * @param response 외부 API로부터 받은 JSON 데이터
     * @return ExchangeRate 객체
     */
    private ExchangeRate jsonParser(Countries country, String response) {
        try {
            JsonNode jsonNode = objectMapper.readTree(response).get(0);
            String currencyName = jsonNode.get("currencyCode").asText();
            if (currencyName.equals(country.getCurrency())) {

                String time = jsonNode.get("date").asText() + jsonNode.get("time").asText();
                double basePrice = jsonNode.get("basePrice").asDouble();
                double toBuy = jsonNode.get("cashBuyingPrice").asDouble();
                double toSell = jsonNode.get("cashSellingPrice").asDouble();
                int currentUnit = jsonNode.get("currencyUnit").asInt();
                String countryName = jsonNode.get("country").asText();

                return ExchangeRate.builder()
                        .time(time)
                        .basePrice(basePrice)
                        .toBuy(toBuy)
                        .toSell(toSell)
                        .currentUnit(currentUnit)
                        .countryName(countryName)
                        .currency(currencyName)
                        .build();
            }

        } catch (Exception e) {
            log.error("json parser error : {}", e.getMessage());
        }
        return null;
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
     * @param currency 국가이름
     */
    public void evictExchangeRateCache(String currency) {
        Cache weatherCache = cacheManager.getCache("exchangeRate");
        if (weatherCache != null) {
            weatherCache.evict("rate_of:" + currency);
        }
    }
}