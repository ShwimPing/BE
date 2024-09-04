package com.shwimping.be.auth.dto.request;

import com.shwimping.be.user.domain.type.Provider;

public record KakaoLoginParams(
        String kakaoAccessToken
) implements OAuthLoginParams {

    @Override
    public Provider oAuthProvider() {
        return Provider.KAKAO;
    }

    @Override
    public String accessToken() {
        return kakaoAccessToken;
    }
}