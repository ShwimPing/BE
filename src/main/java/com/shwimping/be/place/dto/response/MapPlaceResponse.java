package com.shwimping.be.place.dto.response;

import com.shwimping.be.place.domain.MongoPlace;
import com.shwimping.be.place.domain.type.Category;
import lombok.Builder;

@Builder
public record MapPlaceResponse(
        Category category,
        String address,
        double longitude,
        double latitude,
        long placeId
) {

    public static MapPlaceResponse from(MongoPlace mongoPlace) {
        return MapPlaceResponse.builder()
                .category(mongoPlace.getCategory())
                .address(mongoPlace.getAddress())
                .longitude(mongoPlace.getLocation().getX())
                .latitude(mongoPlace.getLocation().getY())
                .placeId(mongoPlace.getPlaceId())
                .build();
    }
}
