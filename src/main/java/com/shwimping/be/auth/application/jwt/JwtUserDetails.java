package com.shwimping.be.auth.application.jwt;

import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Role;
import io.jsonwebtoken.Claims;
import lombok.Builder;

import static com.shwimping.be.user.domain.type.Role.GUEST;

@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails from(Claims claims) {
        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(Role.valueOf(claims.get("role").toString()))
                .build();
    }

    public static JwtUserDetails from(User user) {
        return JwtUserDetails.builder()
                .userId(user.getId())
                .role(GUEST)
                .build();
    }
}
