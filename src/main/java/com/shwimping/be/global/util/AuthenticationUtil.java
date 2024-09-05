package com.shwimping.be.global.util;

import com.shwimping.be.auth.application.jwt.JwtTokenProvider;
import com.shwimping.be.auth.presentation.filter.UserAuthentication;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {

    private final JwtTokenProvider jwtTokenProvider;

    public void setAuthenticationFromRequest(HttpServletRequest request, String token) {
        Authentication authentication = makeAuthentication(request, token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserAuthentication makeAuthentication(HttpServletRequest request, String token) {
        UserAuthentication authentication = UserAuthentication.from(jwtTokenProvider.getJwtUserDetails(token));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authentication;
    }
}