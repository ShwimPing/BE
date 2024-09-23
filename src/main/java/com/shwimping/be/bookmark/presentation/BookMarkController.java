package com.shwimping.be.bookmark.presentation;

import com.shwimping.be.bookmark.application.BookMarkService;
import com.shwimping.be.global.dto.ResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BookMark", description = "북마크 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/bookmarks")
public class BookMarkController {

    private final BookMarkService bookMarkService;

    @Operation(summary = "북마크 저장/삭제", description = "북마크 저장/삭제")
    @GetMapping("/{placeId}")
    public ResponseEntity<ResponseTemplate<?>> saveBookMark(
            @AuthenticationPrincipal Long userId,
            @PathVariable Long placeId
    ) {
        bookMarkService.saveBookMark(userId, placeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.EMPTY_RESPONSE);
    }
}
