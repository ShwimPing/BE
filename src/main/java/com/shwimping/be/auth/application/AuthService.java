package com.shwimping.be.auth.application;

import com.shwimping.be.auth.application.exception.InvalidTokenException;
import com.shwimping.be.auth.application.jwt.JwtTokenProvider;
import com.shwimping.be.auth.application.jwt.JwtUserDetails;
import com.shwimping.be.auth.application.jwt.Tokens;
import com.shwimping.be.auth.dto.request.LoginRequest;
import com.shwimping.be.auth.dto.response.LoginResponse;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.domain.User;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import com.shwimping.be.user.exception.InvalidPasswordException;
import com.shwimping.be.user.exception.UserNotFoundException;
import com.shwimping.be.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.shwimping.be.auth.application.exception.errorcode.AuthErrorCode.INVALID_TOKEN;
import static com.shwimping.be.auth.application.jwt.type.JwtValidationType.VALID_JWT;
import static com.shwimping.be.user.exception.errorcode.UserErrorCode.INVALID_PASSWORD;
import static com.shwimping.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    public JwtUserDetails getJwtUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        return JwtUserDetails.from(user);
    }

    public void signUp(CreateUserRequest request) {
        userService.createUser(request);
    }

    public LoginResponse selfLogin(LoginRequest request, HttpServletResponse response) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidPasswordException(INVALID_PASSWORD);
        }

        Tokens tokens = jwtTokenProvider.generateToken(getJwtUserDetails(user.getId()));
        response.setHeader("refresh-token", tokens.refreshToken());

        return LoginResponse.from(tokens);
    }

    public LoginResponse reIssueToken(String refreshToken) {
        if (jwtTokenProvider.validateToken(refreshToken) == VALID_JWT) {
            Long userId = jwtTokenProvider.getJwtUserDetails(refreshToken).userId();
            return LoginResponse.from(jwtTokenProvider.generateToken(getJwtUserDetails(userId)));
        } else {
            throw new InvalidTokenException(INVALID_TOKEN);
        }
    }

    public void withdraw(Long userId) {
        userService.deleteUser(userId);
    }

    public String getTestToken(Long userId) {
        return jwtTokenProvider.generateToken(getJwtUserDetails(userId)).accessToken();
    }
}