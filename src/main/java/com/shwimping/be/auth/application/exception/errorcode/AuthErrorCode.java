package com.shwimping.be.auth.application.exception.errorcode;

import com.shwimping.be.global.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 인증/인가 관련 error 해결을 위한 enum class
 */
@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "Token is not in the HTTP Header."),
    UNSUPPORTED_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "Unsupported token type."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "The token is missing or invalid."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "Token is not properly formed."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "Expired token."),
    TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "Login information does not match token information."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access is denied."),
    TOKEN_NOT_EXIST(HttpStatus.UNAUTHORIZED, "this access token does not exist"),
    FEIGN_FAILED(HttpStatus.BAD_REQUEST, "Failed to call social login API."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Invalid refresh token."),
    INVALID_PROVIDER(HttpStatus.BAD_REQUEST, "Invalid provider."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}