package com.shwimping.be.auth.application;

import com.shwimping.be.auth.dto.request.OAuthLoginRequest;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.auth.util.OAuthApiClient;
import com.shwimping.be.user.domain.type.Provider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class RequestOAuthInfoService {

    private final Map<String, OAuthApiClient> clients;

    public OAuthInfoResponse request(Provider provider, OAuthLoginRequest request) {
        OAuthApiClient client = clients.get(provider.getProvider());
        String accessToken = client.requestAccessToken(request);
        return client.requestOauthInfo(accessToken);
    }
}
