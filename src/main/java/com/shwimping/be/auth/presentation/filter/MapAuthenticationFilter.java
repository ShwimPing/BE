package com.shwimping.be.auth.presentation.filter;

import static com.shwimping.be.auth.application.exception.errorcode.AuthErrorCode.INVALID_TOKEN;
import static com.shwimping.be.auth.application.jwt.type.JwtValidationType.VALID_JWT;

import com.shwimping.be.auth.application.jwt.JwtTokenProvider;
import com.shwimping.be.global.util.AuthenticationUtil;
import com.shwimping.be.global.util.ErrorResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class MapAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationUtil authenticationUtil;

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        // 특정 URI에 대해서만 필터링
        if (requestURI.startsWith("/place/nearby")) {
            // 요청 헤더의 Authorization 키 조회
            String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
            // 가져온 값에서 접두사 제거
            String token = getAccessToken(authorizationHeader);

            // 토큰이 있는 경우 가져온 토큰이 유효한지 확인
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token) != VALID_JWT) {
                ErrorResponseUtil.setResponse(response, INVALID_TOKEN);
                return; // 필터 체인 진행 중단
            }

            //토큰이 있다면 해당 유저의 정보 주입, 없다면 임의의 Authentication 객체 생성
            if (StringUtils.hasText(token)) {
                // JWT 토큰을 사용하여 Authentication 객체 생성
                authenticationUtil.setAuthenticationFromRequest(request, token);
            } else{   //토큰이 없는 경우
                // 토큰이 없을 경우 임의의 Authentication 객체 생성
                authenticationUtil.setDummyAuthentication(request);
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
