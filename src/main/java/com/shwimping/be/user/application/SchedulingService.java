package com.shwimping.be.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final WeatherService weatherService;

    // 매일 오후 4시 5분에 실행
    @Scheduled(cron = "0 5 16 * * ?")
    public void sendWeatherWarning() {
        weatherService.getWeatherWarning();
    }
}