package com.shwimping.be.user.dto.request;

import com.shwimping.be.global.util.NCPProperties;
import com.shwimping.be.user.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.shwimping.be.user.domain.type.Provider.SELF;

public record CreateUserRequest(
    @Email(message = "이메일 형식을 맞춰주세요")
    String email,
    @Size(min = 8, message = "비밀번호를 8자 이상 입력해주세요")
    @NotBlank(message = "비밀번호를 입력해주세요")
    String password
) {
    public User toUser(PasswordEncoder passwordEncoder, NCPProperties ncpProperties) {
        String temporal = "temporal";

        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .fcmToken(temporal)
                .provider(SELF)
                .nickname(temporal)
                .isAlarmAllowed(true)
                .profileImageUrl(ncpProperties.s3().endpoint() + ncpProperties.s3().bucket() + "/profile/ic_profile.svg")
                .nowLocation(temporal)
                .build();
    }
}