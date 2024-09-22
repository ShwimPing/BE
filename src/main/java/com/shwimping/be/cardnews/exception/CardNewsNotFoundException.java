package com.shwimping.be.cardnews.exception;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardNewsNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}