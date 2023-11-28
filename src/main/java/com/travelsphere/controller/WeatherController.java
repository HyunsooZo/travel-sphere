package com.travelsphere.controller;

import com.travelsphere.service.WeatherService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Weather API", description = "날씨정보 관련 API")
@RequestMapping("/api/v1/weathers")
@RequiredArgsConstructor
@RestController
public class WeatherController {
    private final WeatherService weatherService;
}
