package com.shwimping.be.auth.dto.request;

import com.shwimping.be.user.domain.type.Provider;

public interface OAuthLoginParams {
    Provider oAuthProvider();
    String accessToken();
}