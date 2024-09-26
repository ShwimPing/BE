package com.shwimping.be.user.presentation;

import com.shwimping.be.bookmark.application.BookMarkService;
import com.shwimping.be.bookmark.dto.response.BookMarkPlaceResponseList;
import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.review.application.ReviewService;
import com.shwimping.be.review.dto.response.ReviewSimpleResponseList;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.dto.request.UpdateProfileRequest;
import com.shwimping.be.user.dto.response.MypageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.shwimping.be.global.dto.ResponseTemplate.EMPTY_RESPONSE;

@Tag(name = "Mypage", description = "마이페이지 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final BookMarkService bookMarkService;

    @Operation(summary = "마이페이지 조회", description = "토큰이 없는 유저(401)의 경우 '로그인 후 사용하세요' 등의 메세지를 띄워 해주세요")
    @GetMapping
    public ResponseEntity<ResponseTemplate<?>> getMypage(
            @AuthenticationPrincipal Long userId) {

        MypageResponse response = userService.getMypage(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(response));
    }

    @Operation(summary = "프로필 수정", description = "프로필 수정 기능")
    @PutMapping(value = "/profile", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseTemplate<?>> updateProfile(
            @AuthenticationPrincipal Long userId,
            @Valid @RequestPart UpdateProfileRequest request,
            @RequestPart(required = false) MultipartFile file) {

        userService.updateProfile(userId, request, file);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }

    @Operation(summary = "북마크 조회", description = "북마크 처음 조회 시 lastBookMarkId에 0을 넣어주세요<br>" +
            "최근에 북마크를 한 순서대로 제공됩니다")
    @GetMapping("/bookmark")
    public ResponseEntity<ResponseTemplate<?>> getMyBookmark(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long lastBookMarkId,
            @RequestParam(defaultValue = "5") Long size) {

        BookMarkPlaceResponseList responseList = bookMarkService.getMyBookMark(userId, lastBookMarkId, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responseList));
    }

    @Operation(summary = "내 리뷰 모아보기", description = "리뷰 처음 조회 시 lastReviewId에 0을 넣어주세요<br>" +
            "리뷰는 최신순으로 제공됩니다")
    @GetMapping("/review")
    public ResponseEntity<ResponseTemplate<?>> getMyReview(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long lastReviewId,
            @RequestParam(defaultValue = "5") Long size) {

        ReviewSimpleResponseList responseList = reviewService.getMyReview(userId, lastReviewId, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(responseList));
    }

    @Operation(summary = "푸시 알림 설정", description = "푸시 알림 on/off 설정 기능")
    @PostMapping("/alarm")
    public ResponseEntity<ResponseTemplate<?>> updateAlarm(
            @AuthenticationPrincipal Long userId) {

        userService.updateAlarm(userId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(EMPTY_RESPONSE);
    }
}