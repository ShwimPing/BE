package com.shwimping.be.place.exception;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
}
