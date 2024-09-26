package com.shwimping.be.auth.util.kakao;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.auth.util.OAuthApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component("kakao")
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private final KakaoUserClient kakaoUserClient;

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        return kakaoUserClient.requestOauthInfo("Bearer " + accessToken, body);
    }
}