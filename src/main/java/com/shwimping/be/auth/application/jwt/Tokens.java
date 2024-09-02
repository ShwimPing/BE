package com.shwimping.be.auth.application.jwt;

public record Tokens(
        String accessToken,
        String refreshToken
) {
}