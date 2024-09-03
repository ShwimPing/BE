package com.shwimping.be.auth.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth.client.registration.kakao")
public record KakaoProperties(
        String clientId,
        String clientSecret,
        Url url
) {
    public record Url(String auth, String api) {
    }
}