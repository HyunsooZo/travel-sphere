package com.travelsphere.service;

import com.travelsphere.repository.StatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
}
