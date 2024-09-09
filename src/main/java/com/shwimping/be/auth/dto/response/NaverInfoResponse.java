package com.shwimping.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.shwimping.be.user.domain.type.Provider;
import lombok.Builder;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record NaverInfoResponse(
        Response response
) implements OAuthInfoResponse {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @Builder
    public record Response(
            String id,
            String nickname,
            String email
    ) {
    }

    @Override
    public String getId() {
        return response.id();
    }

    @Override
    public String getNickname() {
        return response.nickname();
    }

    @Override
    public String getEmail() {
        return response.email();
    }

    @Override
    public Provider getOAuthProvider() {
        return Provider.NAVER;
    }
}