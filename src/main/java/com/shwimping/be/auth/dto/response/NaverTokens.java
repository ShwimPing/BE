package com.shwimping.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NaverTokens(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("expires_in") String expiresIn
) {
}
