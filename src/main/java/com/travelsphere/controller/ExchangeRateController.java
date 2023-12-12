package com.travelsphere.controller;

import com.travelsphere.dto.ExchangeCalculateResponseDto;
import com.travelsphere.dto.ExchangeRateDto;
import com.travelsphere.dto.ExchangeRateResponseDto;
import com.travelsphere.enums.Countries;
import com.travelsphere.enums.Currencies;
import com.travelsphere.service.ExchangeRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Api(tags = "Exchange Rate API", description = "환율 관련 API")
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
@RestController
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    /**
     * 환율 조회
     *
     * @param countryName 국가명
     * @param token       토큰
     * @return 환율 정보
     */
    @GetMapping("/country/{countryName}")
    @ApiOperation(value = "환율 조회", notes = "환율을 조회합니다.")
    public ResponseEntity<ExchangeRateResponseDto> getExchangeRates(
            @PathVariable Countries countryName,
            @RequestHeader(AUTHORIZATION) String token) {

        List<ExchangeRateDto> rates =
                exchangeRateService.getExchangeRates(countryName);

        return ResponseEntity.status(OK).body(ExchangeRateResponseDto.from(rates));
    }

    /**
     * 환율 계산
     * @param fromCurrency 입력된 화폐
     * @param toCurrency 출력할 화폐
     * @param amount 금액
     * @param token 토큰
     * @return 환율 계산 결과
     */
    @GetMapping("/from/{fromCurrency}/to/{toCurrency}/amount/{amount}")
    @ApiOperation(value = "환율 계산", notes = "원화를 외화로(또는 반대로) 계산합니다.")
    public ResponseEntity<ExchangeCalculateResponseDto> getForeignCurrency(
            @PathVariable Currencies fromCurrency,
            @PathVariable Currencies toCurrency,
            @PathVariable double amount,
            @RequestHeader(AUTHORIZATION) String token) {

        Double calculatedAmount =
                exchangeRateService.convertCurrency(fromCurrency, toCurrency, amount);

        return ResponseEntity.status(OK)
                .body(ExchangeCalculateResponseDto.from(calculatedAmount));
    }
}
