package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.response.KakaoInfoResponse;
import com.shwimping.be.auth.dto.response.KakaoTokens;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.domain.type.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component("kakao")
@RequiredArgsConstructor
public class KakaoApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final KakaoProperties kakaoProperties;
    private final RestTemplate restTemplate;

    @Override
    public Provider oAuthProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String requestAccessToken(OAuthLoginParams params) {
        log.info("url: {}", kakaoProperties.url().auth() + "/oauth/token");
        String url = kakaoProperties.url().auth() + "/oauth/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", kakaoProperties.clientId());

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        log.info("request: {}", request);

        KakaoTokens response = restTemplate.postForObject(url, request, KakaoTokens.class);

        assert response != null;
        return response.accessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        String url = kakaoProperties.url().api() + "/v2/user/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, KakaoInfoResponse.class);
    }
}