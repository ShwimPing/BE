package com.shwimping.be.auth.presentation;

import com.shwimping.be.auth.application.AuthService;
import com.shwimping.be.auth.dto.request.LoginRequest;
import com.shwimping.be.auth.dto.request.OAuthLoginRequest;
import com.shwimping.be.auth.dto.response.LoginResponse;
import com.shwimping.be.auth.dto.response.ReissueResponse;
import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.user.domain.type.Provider;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import com.shwimping.be.user.dto.request.SaveProfileRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Auth", description = "Auth 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "소셜 로그인 / 회원가입", description = "소셜 로그인 및 회원가입<br>" +
            "사용자가 로그인 연동 후 받게 되는 Access Token을 넣어주세요.")
    @PostMapping("/login/{provider}")
    public ResponseEntity<ResponseTemplate<Object>> socialLogin(
            @PathVariable Provider provider,
            @RequestBody OAuthLoginRequest request,
            HttpServletResponse response) {

        LoginResponse socialResponse = authService.socialLogin(provider, request, response);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(socialResponse));
    }

    @Operation(summary = "자체 회원가입", description = "소셜 로그인 없는 자체 회원가입")
    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<Object>> signUp(
            @Valid @RequestBody CreateUserRequest request) {

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

    @Operation(summary = "닉네임 중복 검사", description = "닉네임이 존재하면 true, 존재하지 않으면 false를 반환합니다")
    @GetMapping("/nickname/validation")
    public ResponseEntity<ResponseTemplate<Object>> validateNickname(
            @Valid @RequestParam String nickname) {

        boolean response = authService.validateNickname(nickname);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "프로필 등록", description = "회원가입 후 닉네임, 프로필 사진, 토큰을 등록합니다")
    @PostMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseTemplate<Object>> saveProfile(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestPart SaveProfileRequest request,
            @RequestPart(required = false) MultipartFile file) {

        authService.saveProfile(userId, request, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "Access Token 재발급", description = "토큰 재발급시 Authorization에 'Bearer ' 없이 refresh Token만 입력해주세요")
    @PostMapping("/reissue")
    public ResponseEntity<ResponseTemplate<Object>> reIssueToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String refreshToken) {

        ReissueResponse response = authService.reIssueToken(refreshToken);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "회원탈퇴", description = "회원탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity<ResponseTemplate<Object>> withdraw(
            @AuthenticationPrincipal Long userId) {

        authService.withdraw(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "테스트용 토큰발급", description = "테스트용 토큰발급")
    @GetMapping("/test/{userId}")
    public String testToken(@PathVariable Long userId) {
        return authService.getTestToken(userId);
    }
}