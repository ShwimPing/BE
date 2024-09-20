package com.shwimping.be.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final WeatherService weatherService;

    // 매 시간의 3분에 api 요청
    @Scheduled(cron = "0 3 * * * ?")
    public void sendWeatherWarning() {
        weatherService.getWeatherWarning();
    }
}