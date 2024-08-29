package com.shwimping.be.auth.dto.request;

import jakarta.validation.constraints.Email;

public record LoginRequest(
        @Email(message = "이메일 형식을 맞춰주세요")
        String email,
        String password
) {
}