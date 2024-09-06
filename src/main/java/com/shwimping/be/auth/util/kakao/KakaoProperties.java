package com.shwimping.be.auth.util.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth.kakao")
public record KakaoProperties(
        String clientId,
        String clientSecret,
        String redirectUri
) {
}