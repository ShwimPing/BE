package com.shwimping.be.auth.util.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoTokens(
        String accessToken,
        String tokenType,
        String refreshToken,
        String expiresIn,
        String refreshTokenExpiresIn,
        String scope
) {
}