package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;

public interface OAuthApiClient {
    String requestAccessToken(OAuthLoginParams params, String authCode);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
