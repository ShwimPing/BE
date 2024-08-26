package com.shwimping.be.user.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.dto.request.CreateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "User", description = "유저 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Operation(summary = "자체 회원가입", description = "자체 로그인에서 회원가입하기")
    @PostMapping("/signup")
    public ResponseEntity<ResponseTemplate<Object>> createUser(@RequestBody CreateUserRequest request) {
        userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}