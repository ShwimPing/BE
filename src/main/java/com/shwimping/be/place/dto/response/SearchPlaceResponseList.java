package com.shwimping.be.place.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record SearchPlaceResponseList(
        Long page,
        Boolean hasNext,
        Double longitude,
        Double latitude,
        List<SearchPlaceResponse> placeList
) {
    public static SearchPlaceResponseList of(
            Long page, Boolean hasNext, Double longitude, Double latitude, List<SearchPlaceResponse> placeList) {
        return SearchPlaceResponseList.builder()
                .page(page)
                .placeList(placeList)
                .longitude(longitude)
                .latitude(latitude)
                .hasNext(hasNext)
                .build();
    }
}
