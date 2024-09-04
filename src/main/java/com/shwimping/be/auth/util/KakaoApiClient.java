package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.response.KakaoInfoResponse;
import com.shwimping.be.user.domain.type.Provider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoApiClient", url = "${kakao.feign.base-url}")
@Component
public interface KakaoApiClient extends OAuthApiClient {

    @Override
    @GetMapping(value = "/v2/user/me")
    KakaoInfoResponse requestOauthInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);

    @Override
    default Provider oAuthProvider() {
        return Provider.KAKAO;
    }
}