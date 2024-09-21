package com.shwimping.be.review.exception.errorcode;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "Review not found"),
    CANNOT_DELETE_REVIEW(HttpStatus.FORBIDDEN, "Author of the review can only delete the review"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}