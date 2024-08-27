package com.shwimping.be.auth.application.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@AllArgsConstructor
public class JwtProperties {
    private String secretKey;
    private String issuer;
    private Long accessTokenExpiration;
    private Long refreshTokenExpiration;
}