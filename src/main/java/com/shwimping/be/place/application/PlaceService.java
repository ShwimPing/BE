package com.shwimping.be.place.application;

import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.MapPlaceResponse;
import com.shwimping.be.place.dto.response.SearchPlaceResponseList;
import com.shwimping.be.place.repository.PlaceRepository;
import com.shwimping.be.place.repository.mongo.MongoPlaceRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PlaceService {

    private final MongoPlaceRepository mongoPlaceRepository;
    private final PlaceRepository placeRepository;

    public List<MapPlaceResponse> findPlacesWithinRadius(
            double longitude, double latitude, double radius, List<Category> category) {

        return mongoPlaceRepository.findAllByLocationNear(longitude, latitude, radius, category).stream()
                .map(MapPlaceResponse::from)
                .toList();
    }

    public SearchPlaceResponseList findNearestPlaces(
            double longitude, double latitude, int maxDistant, List<Category> categoryList, SortType sortType, long page) {

        return SearchPlaceResponseList.of(page,
                placeRepository.findAllByLocationWithDistance(longitude, latitude, maxDistant, categoryList, sortType, page));
    }
}
