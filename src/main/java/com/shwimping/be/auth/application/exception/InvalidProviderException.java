package com.shwimping.be.auth.application.exception;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InvalidProviderException extends RuntimeException {
    private final ErrorCode errorCode;
}