package com.shwimping.be.place.repository;

import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import java.util.List;

public interface PlaceRepositoryCustom {
    List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, double maxDistance);
}
