package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.domain.type.Provider;

public interface OAuthApiClient {
    Provider oAuthProvider();
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
