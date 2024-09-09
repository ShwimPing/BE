package com.shwimping.be.place.repository;

import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.PlaceDetailWithReviews;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import java.util.List;

public interface PlaceRepositoryCustom {
    List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<Category> categoryList, SortType sortType,
            String keyword, long page, long size);

    Long countByLocationWithDistance(double longitude, double latitude, int maxDistant, List<Category> categoryList);

    PlaceDetailWithReviews findPlaceDetail(Long placeId, Long userId, Long size);
}
