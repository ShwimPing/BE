package com.shwimping.be.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record OAuthLoginRequest(
        @NotBlank(message = "Access Token을 입력해주세요")
        String accessToken
) {
}