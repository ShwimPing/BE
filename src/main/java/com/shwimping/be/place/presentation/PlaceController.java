package com.shwimping.be.place.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.place.application.AISearchFacade;
import com.shwimping.be.place.application.PlaceService;
import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.MapPlaceResponseList;
import com.shwimping.be.place.dto.response.SearchPlaceResponseList;
import com.shwimping.be.user.application.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Place", description = "지도, 장소 관련 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/places")
public class PlaceController {

    private final UserService userService;
    private final PlaceService placeService;
    private final AISearchFacade aiSearchFacade;

    @Operation(summary = "주변 장소 검색", description = "경도, 위도, 거리를 기반으로 검색, 거리는 m 단위로 입력<br>" +
            "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116(도서관 쉼터), region은 XX구를 의미하며 재난 경보 푸쉬 알림에서 사용")
    @GetMapping("/nearby")
    public ResponseEntity<ResponseTemplate<?>> getNearbyPlaces(
            @AuthenticationPrincipal Long userId,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius,
            @RequestParam List<Category> category,
            @RequestParam String region) {

        MapPlaceResponseList placesWithinRadius =
                MapPlaceResponseList.from(placeService.findPlacesWithinRadius(longitude, latitude, radius, category));

        userService.updateUserLocation(userId, region);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placesWithinRadius));
    }

    @Operation(summary = "검색", description = "경도, 위도, 카테고리, 정렬 기준을 기반으로 검색, page는 0부터 시작, hasNext가 false이면 다음 데이터 X<br>>" +
            "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116(도서관 쉼터)")
    @GetMapping("/search")
    public ResponseEntity<ResponseTemplate<?>> searchPlaces(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam int maxDistance,
            @RequestParam List<Category> category,
            @RequestParam SortType sortType,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam int page,
            @RequestParam(defaultValue = "10") int size) {

        SearchPlaceResponseList nearestPlaces =
                placeService.findNearestPlaces(longitude, latitude, maxDistance, category, sortType, keyword, page, size);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(nearestPlaces));
    }

    @Operation(summary = "AI 챗봇", description = "AI 챗봇을 통해 사용자의 요청에 대한 응답을 받음<br>" +
            "예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116(도서관 쉼터), message는 사용자의 요청을 의미<br>" +
            "예시 요청: 이 근처 3km 안에 이름에 못골이 들어간 쉴 곳을 추천해줘. 평점순으로<br>" +
            "paging을 통해서 다음 페이지를 보려면 searchPlaces API에 요청")
    @GetMapping("/ai-search")
    public ResponseEntity<ResponseTemplate<?>> getAIResponse(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam String message) {

        SearchPlaceResponseList shelterRecommendAI = aiSearchFacade.getShelterRecommendAI(longitude, latitude, message);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(shelterRecommendAI));
    }

    @Operation(summary = "장소 상세 조회", description = "장소 상세 정보와 리뷰를 조회")
    @GetMapping("/detail")
    public ResponseEntity<ResponseTemplate<?>> getPlaceDetail(
            @AuthenticationPrincipal Long userId,
            @RequestParam Long placeId,
            @RequestParam(defaultValue = "5") Long size
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placeService.findPlaceDetail(userId, placeId, size)));
    }
}
