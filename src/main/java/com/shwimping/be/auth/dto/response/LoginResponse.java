package com.shwimping.be.auth.dto.response;

import com.shwimping.be.auth.application.jwt.Tokens;
import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        String refreshToken
) {
    public static LoginResponse of(Tokens token) {
        return LoginResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
