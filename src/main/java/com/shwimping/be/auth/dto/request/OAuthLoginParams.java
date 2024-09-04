package com.shwimping.be.auth.dto.request;

import com.shwimping.be.user.domain.type.Provider;
import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    Provider oAuthProvider();
    MultiValueMap<String, String> makeBody();
}