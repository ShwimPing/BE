package com.shwimping.be.auth.dto.response;

import com.shwimping.be.auth.application.jwt.Tokens;
import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken
) {
    public static LoginResponse from(Tokens token) {
        return LoginResponse.builder()
                .accessToken(token.accessToken())
                .build();
    }
}
