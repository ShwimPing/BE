package com.shwimping.be.auth.util.naver;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.auth.util.OAuthApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Slf4j
@Component("naver")
@RequiredArgsConstructor
public class NaverApiClient implements OAuthApiClient {

    private final NaverUserClient naverUserClient;

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        return naverUserClient.requestOauthInfo("Bearer " + accessToken, body);
    }
}