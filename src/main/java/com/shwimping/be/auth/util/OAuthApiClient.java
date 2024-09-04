package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.domain.type.Provider;

public interface OAuthApiClient {
    Provider oAuthProvider();
    String requestAccessToken(OAuthLoginParams params);
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
