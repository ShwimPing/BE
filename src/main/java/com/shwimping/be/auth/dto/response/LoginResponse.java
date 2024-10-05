package com.shwimping.be.auth.dto.response;

import com.shwimping.be.auth.application.jwt.Tokens;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Role;
import lombok.Builder;

@Builder
public record LoginResponse(
        Role role,
        String accessToken
) {
    public static LoginResponse from(Tokens token, User user) {
        return LoginResponse.builder()
                .role(user.getRole())
                .accessToken(token.accessToken())
                .build();
    }
}
