package com.shwimping.be.auth.dto.request;

import com.shwimping.be.user.domain.type.Provider;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public record NaverLoginParams(
        String authCode,
        String state,
        Provider oAuthProvider
) implements OAuthLoginParams {

    @Override
    public Provider oAuthProvider() {
        return Provider.NAVER;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authCode);
        body.add("state", state);
        return body;
    }
}