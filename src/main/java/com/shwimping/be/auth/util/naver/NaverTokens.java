package com.shwimping.be.auth.util.naver;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record NaverTokens(
        String accessToken,
        String refreshToken,
        String tokenType,
        String expiresIn
) {
}