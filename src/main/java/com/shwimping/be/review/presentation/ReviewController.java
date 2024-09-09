package com.shwimping.be.review.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.review.application.ReviewService;
import com.shwimping.be.review.dto.response.ReviewSimpleResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Review", description = "리뷰 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 리스트 조회
    @Operation(summary = "리뷰 리스트 조회", description = "장소별 리뷰 리스트 조회, lastReviewId를 기준으로 이전 리뷰 조회, 리뷰는 최신순으로 정렬<br>" +
            "lastReviewId 이전(시간순서) review부터 size만큼 리뷰를 가져옴, place 511번이 더미 데이터 존재")
    @GetMapping("/{placeId}")
    public ResponseEntity<ResponseTemplate<?>> getReviewList(
            @PathVariable Long placeId,
            @RequestParam Long lastReviewId,
            @RequestParam Long size) {

        ReviewSimpleResponseList reviewSimpleResponse =
                reviewService.getReviewSimpleResponse(placeId, lastReviewId, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(reviewSimpleResponse));
    }
}
