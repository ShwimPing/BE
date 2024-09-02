package com.shwimping.be.place.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.place.application.PlaceService;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.MapPlaceResponseList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Place", description = "지도 관련 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "주변 장소 검색", description = "경도, 위도, 거리를 기반으로 검색, 거리는 m 단위로 입력 - 예시 데이터 경도: 127.0965824, 위도: 37.47153792 - 서울특별시 강남구 자곡로 116(도서관 쉼터)")
    @GetMapping("/nearby")
    public ResponseEntity<ResponseTemplate<?>> getNearbyPlaces(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius,
            @RequestParam List<Category> category) {

        MapPlaceResponseList placesWithinRadius =
                MapPlaceResponseList.from(placeService.findPlacesWithinRadius(longitude, latitude, radius, category));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placesWithinRadius));
    }
}
