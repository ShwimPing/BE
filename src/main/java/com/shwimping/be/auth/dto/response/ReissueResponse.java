package com.shwimping.be.auth.dto.response;

import com.shwimping.be.auth.application.jwt.Tokens;
import lombok.Builder;

@Builder
public record ReissueResponse(
        String accessToken
) {
    public static ReissueResponse from(Tokens token) {
        return ReissueResponse.builder()
                .accessToken(token.accessToken())
                .build();
    }
}