package com.shwimping.be.auth.util;

import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.domain.type.Provider;

public interface OAuthApiClient {
    Provider oAuthProvider();
    // Authorization Code 를 기반으로 인증 API 를 요청해서 Access Token 을 획득
    String requestAccessToken(OAuthLoginParams params);
    // Access Token 을 기반으로 Email, Nickname 이 포함된 프로필 정보를 획득
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
