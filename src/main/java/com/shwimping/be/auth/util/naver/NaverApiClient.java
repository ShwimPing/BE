package com.shwimping.be.auth.util.naver;

import com.shwimping.be.auth.application.exception.InvalidTokenException;
import com.shwimping.be.auth.dto.request.OAuthLoginRequest;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.auth.util.OAuthApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.UUID;

import static com.shwimping.be.auth.application.exception.errorcode.AuthErrorCode.INVALID_TOKEN;

@Slf4j
@Component("naver")
@RequiredArgsConstructor
public class NaverApiClient implements OAuthApiClient {

    private static final String GRANT_TYPE = "authorization_code";

    private final NaverProperties naverProperties;
    private final NaverTokenClient naverTokenClient;
    private final NaverUserClient naverUserClient;

    @Override
    public String requestAccessToken(OAuthLoginRequest request) {
        NaverTokens response = naverTokenClient.requestAccessToken(GRANT_TYPE, naverProperties.clientId(),
                naverProperties.clientSecret(), request.authCode(), generateState());

        if (!ObjectUtils.allNotNull(response)) {
            throw new InvalidTokenException(INVALID_TOKEN);
        }

        return response.accessToken();
    }

    @Override
    public OAuthInfoResponse requestOauthInfo(String accessToken) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        return naverUserClient.requestOauthInfo("Bearer " + accessToken, body);
    }

    private String generateState() {
        return UUID.randomUUID().toString();
    }
}