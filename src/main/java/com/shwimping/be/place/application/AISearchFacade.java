package com.shwimping.be.place.application;

import com.shwimping.be.place.dto.response.GetShelterRecommendAIResponse;
import com.shwimping.be.place.dto.response.SearchPlaceResponseList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AISearchFacade {

    private final AIService aiService;
    private final PlaceService placeService;

    public SearchPlaceResponseList getShelterRecommendAI(double longitude, double latitude, String message) {

        GetShelterRecommendAIResponse response = aiService.getResponse(message);

        return placeService.findNearestPlaces(longitude, latitude, response.distance(), response.category(),
                response.sortType(), response.keyWord(), 0L, 10L);
    }
}
