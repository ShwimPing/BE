package com.shwimping.be.user.dto.request;

import com.shwimping.be.user.domain.User;
import jakarta.validation.constraints.Email;

import static com.shwimping.be.user.domain.type.Provider.SELF;

public record CreateUserRequest(
    @Email(message = "이메일 형식을 맞춰주세요")
    String email,
    String password,
    String fcmToken
) {
    public User toUser(String encodedPassword) {
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .fcmToken(fcmToken)
                .provider(SELF)
                .nickname("temporal")
                .isAlarmAllowed(true)
                .profileImageUrl("temporal.png")
                .nowLocation("temporal")
                .build();
    }
}