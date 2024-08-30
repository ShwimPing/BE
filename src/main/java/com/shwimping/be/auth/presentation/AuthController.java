package com.shwimping.be.auth.presentation;

import com.shwimping.be.auth.application.AuthService;
import com.shwimping.be.auth.dto.request.LoginRequest;
import com.shwimping.be.auth.dto.response.LoginResponse;
import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "자체 회원가입", description = "소셜 로그인 없는 자체 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<Object>> signUp(@Valid @RequestBody CreateUserRequest request) {

        authService.signUp(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "자체 로그인", description = "소셜 로그인 없는 자체 로그인")
    @PostMapping("/login")
    public ResponseEntity<ResponseTemplate<Object>> selfLogin(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response) {

        LoginResponse loginResponse = authService.selfLogin(request, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(loginResponse));
    }

    @Operation(summary = "테스트용 토큰발급", description = "테스트용 토큰발급")
    @GetMapping("/test-token")
    public String testToken(@RequestParam Long userId) {
        return authService.getTestToken(userId);
    }
}