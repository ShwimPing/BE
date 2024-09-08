package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.request.OAuthLoginRequest;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;

public interface OAuthApiClient {
    String requestAccessToken(OAuthLoginRequest request);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
