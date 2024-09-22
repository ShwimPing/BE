package com.shwimping.be.user.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.dto.response.MypageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Mypage", description = "마이페이지 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {

    private final UserService userService;

    @Operation(summary = "마이페이지 조회", description = "마이페이지 조회")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getMypage(
            @AuthenticationPrincipal Long userId) {

        MypageResponse response = userService.getMypage(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "푸시 알림 설정", description = "푸시 알림 on/off 설정 기능")
    @PostMapping("/alarm")
    public ResponseEntity<ResponseTemplate<?>> updateAlarm(
            @AuthenticationPrincipal Long userId) {

        userService.updateAlarm(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}