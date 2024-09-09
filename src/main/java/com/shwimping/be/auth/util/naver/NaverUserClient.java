package com.shwimping.be.auth.util.naver;

import com.shwimping.be.auth.dto.response.NaverInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "naverUserClient", url = "${spring.security.oauth.naver.feign.user-url}")
public interface NaverUserClient {

    @PostMapping(value = "/v1/nid/me", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    NaverInfoResponse requestOauthInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestBody MultiValueMap<String, String> body
    );
}