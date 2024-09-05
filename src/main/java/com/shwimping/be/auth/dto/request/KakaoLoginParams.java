package com.shwimping.be.auth.dto.request;

import com.shwimping.be.user.domain.type.Provider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record KakaoLoginParams(
        String kakaoCode,
        String fcmToken
) implements OAuthLoginParams {

    @Override
    public Provider oAuthProvider() {
        return Provider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", kakaoCode);
        return body;
    }
}