package com.shwimping.be.auth.presentation.filter;

import com.shwimping.be.global.util.ErrorResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.shwimping.be.auth.application.exception.errorcode.AuthErrorCode.ACCESS_DENIED;

/**
 * AccessToken 은 있으나 권한이 맞지 않는 경우 - 403
 */
@Slf4j
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        log.warn("JwtAuthenticationEntryPoint : enter CustomAccessDeniedHandler ");
        ErrorResponseUtil.setResponse(response, ACCESS_DENIED);
    }
}