package com.travelsphere.controller;

import com.travelsphere.dto.StatisticsDto;
import com.travelsphere.dto.StatisticsExpenseResponseDto;
import com.travelsphere.enums.Cities;
import com.travelsphere.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Statistics API", description = "통계 관련 API")
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@RestController
public class StatisticsController {
    private final StatisticsService statisticsService;

    /**
     * 도시별 평균 소비금액 조회
     * @param cityName 도시명
     * @param token jwt 토큰
     * @return ResponseEntity<StatisticsExpenseResponseDto>
     */
    @GetMapping("/cities/{cityName}")
    @ApiOperation(value = "도시별 평균 소비금액 조회", notes = "도시별 평균 소비금액 통계를 조회합니다.")
    public ResponseEntity<StatisticsExpenseResponseDto> getStatisticsByCity(
            @PathVariable Cities cityName,
            @RequestHeader(AUTHORIZATION) String token) {

        List<StatisticsDto> expenses =
                statisticsService.getStatisticsByCity(cityName);

        return ResponseEntity.status(OK)
                .body(StatisticsExpenseResponseDto.from(expenses));
    }
}
