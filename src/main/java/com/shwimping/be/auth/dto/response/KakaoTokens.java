package com.shwimping.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoTokens(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("expires_in") String expiresIn,
        @JsonProperty("refresh_token_expires_in") String refreshTokenExpiresIn,
        @JsonProperty("scope") String scope
) {
}
