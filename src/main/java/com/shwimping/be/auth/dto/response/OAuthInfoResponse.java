package com.shwimping.be.auth.dto.response;

import com.shwimping.be.user.domain.type.Provider;

public interface OAuthInfoResponse {
    String getId();
    String getNickname();
    Provider getOAuthProvider();
}
