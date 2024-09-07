package com.shwimping.be.auth.util.naver;

import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.request.OAuthLoginRequest;
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

    private static final String GRANT_TYPE = "authorization_code";

    private final NaverProperties naverProperties;
    private final NaverTokenClient naverTokenClient;
    private final NaverUserClient naverUserClient;

    @Override
    public String requestAccessToken(OAuthLoginParams params, OAuthLoginRequest request) {
        NaverTokens response = naverTokenClient.requestAccessToken(GRANT_TYPE, naverProperties.clientId(),
                naverProperties.clientSecret(), request.authCode(), request.state());

        assert response != null;
        return response.accessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        return naverUserClient.requestOauthInfo("Bearer " + accessToken, body);
    }
}