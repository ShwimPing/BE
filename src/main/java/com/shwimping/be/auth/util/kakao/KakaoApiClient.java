package com.shwimping.be.auth.util.kakao;

import com.shwimping.be.auth.dto.request.OAuthLoginRequest;
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

    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoProperties kakaoProperties;
    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoUserClient kakaoUserClient;

    @Override
    public String requestAccessToken(OAuthLoginRequest request) {
        KakaoTokens response = kakaoTokenClient.requestAccessToken(GRANT_TYPE, kakaoProperties.clientId(), kakaoProperties.redirectUri(), request.authCode());

        assert response != null;
        return response.accessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        return kakaoUserClient.requestOauthInfo("Bearer " + accessToken, body);
    }
}