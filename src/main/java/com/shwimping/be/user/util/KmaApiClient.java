package com.shwimping.be.user.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kmaApiClient", url = "https://apihub.kma.go.kr")
public interface KmaApiClient {

    @GetMapping("/api/typ01/url/wrn_now_data.php")
    String getWeatherWarning(
            @RequestParam("fe") String fe,
            @RequestParam("tm") String tm,
            @RequestParam("help") String help,
            @RequestParam("authKey") String authKey
    );
}