package com.shwimping.be.auth.util.kakao;

import com.shwimping.be.auth.dto.response.KakaoTokens;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoTokenClient", url = "${spring.security.oauth.kakao.feign.token-url}")
public interface KakaoTokenClient {

    @PostMapping(value = "/oauth/token", consumes = "application/x-www-form-urlencoded")
    KakaoTokens requestAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri,
            @RequestParam("code") String code
    );
}