package com.shwimping.be.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @Email(message = "이메일 형식을 맞춰주세요")
        String email,
        @Size(min = 8, message = "비밀번호를 8자 이상 입력해주세요")
        @NotBlank(message = "비밀번호를 입력해주세요")
        String password
) {
}