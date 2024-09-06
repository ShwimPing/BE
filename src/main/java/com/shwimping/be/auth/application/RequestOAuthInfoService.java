package com.shwimping.be.auth.application;

import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.auth.util.OAuthApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestOAuthInfoService {

    private final Map<String, OAuthApiClient> clients;

    public OAuthInfoResponse request(OAuthLoginParams params, String authCode) {
        OAuthApiClient client = clients.get(params.oAuthProvider().getProvider());
        String accessToken = client.requestAccessToken(params, authCode);
        return client.requestOauthInfo(accessToken);
    }
}
