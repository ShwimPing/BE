package com.shwimping.be.auth.application;

import com.shwimping.be.auth.application.jwt.JwtTokenProvider;
import com.shwimping.be.auth.application.jwt.JwtUserDetails;
import com.shwimping.be.auth.application.jwt.Tokens;
import com.shwimping.be.auth.dto.request.KakaoLoginParams;
import com.shwimping.be.auth.dto.request.OAuthLoginParams;
import com.shwimping.be.auth.dto.response.LoginResponse;
import com.shwimping.be.auth.dto.response.OAuthInfoResponse;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.domain.type.Provider;
import com.shwimping.be.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final UserRepository userRepository;

    public JwtUserDetails getJwtUserDetails(Long userId) {
        User user = userService.getUserById(userId);
        return JwtUserDetails.from(user);
    }

    public LoginResponse socialLogin(Provider provider, KakaoLoginParams params, HttpServletResponse response) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);

        log.info("OAuthInfoResponse: {}", oAuthInfoResponse);

        Long userId = findOrCreateUser(oAuthInfoResponse);
        Tokens tokens = jwtTokenProvider.generateToken(getJwtUserDetails(userId));
        response.setHeader("Refresh-Token", tokens.refreshToken());
        return LoginResponse.from(tokens);
    }

    private Long findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        return userRepository.findBySocialId(oAuthInfoResponse.getId())
                .map(User::getId)
                .orElseGet(() -> newUser(oAuthInfoResponse));
    }

    private Long newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .socialId(oAuthInfoResponse.getId())
                .nickname(oAuthInfoResponse.getNickname())
                .provider(oAuthInfoResponse.getOAuthProvider())
                .email(oAuthInfoResponse.getEmail())
                .profileImageUrl("temporal.png")
                .fcmToken("temporal")
                .isAlarmAllowed(true)
                .nowLocation("temporal")
                .build();

        return userRepository.save(user).getId();
    }
}
