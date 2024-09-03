package com.shwimping.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shwimping.be.user.domain.type.Provider;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoInfoResponse(
        @JsonProperty("kakao_account") KakaoAccount kakaoAccount
) implements OAuthInfoResponse {

    @Override
    public String getEmail() {
        return kakaoAccount.email();
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile().nickname();
    }

    @Override
    public Provider getOAuthProvider() {
        return Provider.KAKAO;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(
            KakaoProfile profile,
            String email
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoProfile(
            String nickname
    ) {}
}