package com.travelsphere.controller;

import com.travelsphere.service.StatisticsService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Statistics API", description = "통계 관련 API")
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@RestController
public class StatisticsController {
    private final StatisticsService statisticsService;
}
