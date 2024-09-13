package com.shwimping.be.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SaveProfileRequest(
        @NotBlank(message = "FCM 토큰을 입력해주세요.")
        String fcmToken,
        @NotBlank(message = "닉네임을 입력해주세요.")
        String nickname
) {
}