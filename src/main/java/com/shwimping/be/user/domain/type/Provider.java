package com.shwimping.be.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    SELF("self"),
    KAKAO("kakao"),
    NAVER("naver"),
    ;

    private final String provider;
}
