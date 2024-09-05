package com.shwimping.be.place.application;

import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.MapPlaceResponse;
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

    public List<MapPlaceResponse> findPlacesWithinRadius(
            double longitude, double latitude, double radius, List<Category> category) {

        return mongoPlaceRepository.findByLocationNear(longitude, latitude, radius, category).stream()
                .map(MapPlaceResponse::from)
                .toList();
    }
}
