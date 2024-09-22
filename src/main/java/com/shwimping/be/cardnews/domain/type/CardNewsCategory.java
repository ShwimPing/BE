package com.shwimping.be.cardnews.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CardNewsCategory {
    HOT("폭염"),
    COLD("한파"),
    OTHER("기타")
    ;

    private final String name;
}
