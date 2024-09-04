package com.shwimping.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.shwimping.be.user.domain.type.Provider;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoInfoResponse (
        String id,
        KakaoAccount kakaoAccount
) implements OAuthInfoResponse {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Builder
    public record KakaoAccount(
            KakaoUserProfile profile,
            String email
    ) {
        public String getNickname() {
            return profile.nickname();
        }

        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public record KakaoUserProfile(
                String nickname,
                String profileImageUrl
        ) {
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.getNickname();
    }

    @Override
    public String getEmail() {
        return getEmail();
    }

    @Override
    public Provider getOAuthProvider() {
        return Provider.KAKAO;
    }
}