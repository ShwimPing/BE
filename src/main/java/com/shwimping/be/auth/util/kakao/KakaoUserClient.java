package com.shwimping.be.auth.util.kakao;

import com.shwimping.be.auth.dto.response.KakaoInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakaoUserClient", url = "${spring.security.oauth.kakao.feign.user-url}")
public interface KakaoUserClient {

    @PostMapping(value = "/v2/user/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoInfoResponse requestOauthInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MultiValueMap<String, String> body
    );
}