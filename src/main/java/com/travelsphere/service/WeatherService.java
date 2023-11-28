package com.travelsphere.service;

import com.travelsphere.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
}
