package com.shwimping.be.place.presentation;

import com.shwimping.be.global.dto.ResponseTemplate;
import com.shwimping.be.place.application.PlaceService;
import com.shwimping.be.place.dto.response.MapPlaceResponseList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/nearby")
    public ResponseEntity<ResponseTemplate<?>> getNearbyPlaces(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double radius) {

        MapPlaceResponseList placesWithinRadius =
                MapPlaceResponseList.from(placeService.findPlacesWithinRadius(longitude, latitude, radius));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ResponseTemplate.from(placesWithinRadius));
    }
}
