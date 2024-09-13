package com.shwimping.be.user.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.user.application.FcmService;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.dto.request.FcmSendRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Mypage", description = "마이페이지 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {

    private final UserService userService;
    private final FcmService fcmService;

    @Operation(summary = "FCM 보내기", description = "FCM 보내기 테스트")
    @PostMapping("/fcm")
    public ResponseEntity<ResponseTemplate<Object>> sendFCM(
            @Valid @RequestBody FcmSendRequest request) {

        fcmService.sendMessage(request);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}