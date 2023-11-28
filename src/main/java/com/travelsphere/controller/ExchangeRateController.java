package com.travelsphere.controller;

import com.travelsphere.service.ExchangeRateService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Exchange Rate API", description = "환율 관련 API")
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
@RestController
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
}
