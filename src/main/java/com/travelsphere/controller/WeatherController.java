package com.travelsphere.controller;

import com.travelsphere.dto.WeatherDto;
import com.travelsphere.dto.WeatherResponseDto;
import com.travelsphere.enums.Cities;
import com.travelsphere.service.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Weather API", description = "날씨정보 관련 API")
@RequestMapping("/api/v1/weathers")
@RequiredArgsConstructor
@RestController
public class WeatherController {
    private final WeatherService weatherService;

    /**
     * 특정도시 날씨정보 조회
     *
     * @param token    토큰
     * @param cityName 도시이름
     * @return WeatherResponseDto
     */
    @GetMapping("/cities/{cityName}")
    @ApiOperation(value = "날씨정보 조회(도시이름 또는 전체)", notes = "도시(들)의 날씨정보를 조회합니다.")
    public ResponseEntity<WeatherResponseDto> getWeather(
            @RequestHeader(AUTHORIZATION) String token,
            @PathVariable Cities cityName) {

        List<WeatherDto> weathers =
                weatherService.getCityWeather(cityName.getCityName());

        return ResponseEntity.status(OK).body(WeatherResponseDto.from(weathers));
    }

}
