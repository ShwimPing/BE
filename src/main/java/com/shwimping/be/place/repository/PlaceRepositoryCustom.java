package com.shwimping.be.place.repository;

import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import java.util.List;

public interface PlaceRepositoryCustom {
    List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<Category> categoryList, SortType sortType,
            long page);
}
