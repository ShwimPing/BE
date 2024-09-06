package com.shwimping.be.auth.util.naver;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth.naver")
public record NaverProperties(
        String clientId,
        String clientSecret,
        String redirectUri
) {
}