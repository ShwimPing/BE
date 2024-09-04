package com.shwimping.be.user.domain.type;

import lombok.Getter;

@Getter
public enum Provider {
    SELF("self"),
    KAKAO("kakao"),
    NAVER("naver"),
    ;

    private final String provider;

    Provider(String provider) {
        this.provider = provider;
    }
}
