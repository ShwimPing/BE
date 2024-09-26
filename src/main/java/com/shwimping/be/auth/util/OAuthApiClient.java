package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.response.OAuthInfoResponse;

public interface OAuthApiClient {
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
