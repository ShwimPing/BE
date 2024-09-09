package com.shwimping.be.place.application;

import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.MapPlaceResponse;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
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

    // 주변 장소 검색
    public List<MapPlaceResponse> findPlacesWithinRadius(
            double longitude, double latitude, double radius, List<Category> category) {

        return mongoPlaceRepository.findAllByLocationNear(longitude, latitude, radius, category).stream()
                .map(MapPlaceResponse::from)
                .toList();
    }

    // 특정 조건 장소 검색
    public SearchPlaceResponseList findNearestPlaces(
            double longitude, double latitude, int maxDistant, List<Category> categoryList, SortType sortType,
            String keyword, long page) {

        List<SearchPlaceResponse> allSearchResult = placeRepository.findAllByLocationWithDistance(
                longitude, latitude, maxDistant, categoryList, sortType, keyword, page);

        // 전체 데이터 수 카운트
        long totalCount = placeRepository.countByLocationWithDistance(longitude, latitude, maxDistant, categoryList);

        // hasNext 계산: 현재 페이지의 데이터 수가 10개이고 전체 데이터 수가 page가 끝나는 지점보다 클 경우 false
        boolean hasNext = allSearchResult.size() == 10 && (page + 1) * 10 < totalCount;

        return SearchPlaceResponseList.of(page, hasNext, longitude, latitude, maxDistant, keyword, sortType,
                categoryList, allSearchResult);
    }
}
