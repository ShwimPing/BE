package com.shwimping.be.user.dto.request;

import com.shwimping.be.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.shwimping.be.user.domain.type.Provider.SELF;

public record CreateUserRequest(
    @Email(message = "이메일 형식을 맞춰주세요")
    String email,
    @Size(min = 8)
    @NotEmpty(message = "비밀번호를 입력해주세요")
    String password,
    @NotEmpty(message = "토큰을 입력해주세요")
    String fcmToken
) {
    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .fcmToken(fcmToken)
                .provider(SELF)
                .nickname("temporal")
                .isAlarmAllowed(true)
                .profileImageUrl("temporal.png")
                .nowLocation("temporal")
                .build();
    }
}