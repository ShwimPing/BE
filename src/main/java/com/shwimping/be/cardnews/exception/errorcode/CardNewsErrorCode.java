package com.shwimping.be.cardnews.exception.errorcode;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardNewsErrorCode implements ErrorCode {

    CARD_NEWS_NOT_FOUND(HttpStatus.NOT_FOUND, "CardNews not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}