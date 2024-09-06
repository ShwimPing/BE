package com.shwimping.be.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OAuthLoginRequest(
        @NotBlank(message = "Code를 입력해주세요")
        String authCode,
        @NotBlank(message = "FCM 토큰을 입력해주세요")
        String fcmToken
) {
}