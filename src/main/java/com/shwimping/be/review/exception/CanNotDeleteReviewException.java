package com.shwimping.be.review.exception;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CanNotDeleteReviewException extends RuntimeException {
    private final ErrorCode errorCode;
}
