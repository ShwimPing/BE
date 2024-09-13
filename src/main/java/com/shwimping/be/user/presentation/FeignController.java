package com.shwimping.be.user.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.user.application.FcmService;
import com.shwimping.be.user.application.WeatherService;
import com.shwimping.be.user.dto.request.FcmSendRequest;
import com.shwimping.be.user.dto.response.WeatherResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Feign", description = "외부 서버와의 통신 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/feign")
public class FeignController {

    private final FcmService fcmService;
    private final WeatherService weatherService;

    @Operation(summary = "FCM 보내기", description = "FCM 보내기 테스트")
    @PostMapping("/fcm")
    public ResponseEntity<ResponseTemplate<Object>> sendFCM(
            @Valid @RequestBody FcmSendRequest request) {

        fcmService.sendMessage(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "특보 현황 조회", description = "기상청의 OpenAPI를 사용하여 특보 현황을 조회합니다.")
    @GetMapping("/weather")
    public ResponseEntity<ResponseTemplate<Object>> getWeatherWarning() {

        List<WeatherResponse> response = weatherService.getWeatherWarning();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }
}