package com.shwimping.be.place.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record MapPlaceResponseList(
        List<MapPlaceResponse> placeList
) {
    public static MapPlaceResponseList from(List<MapPlaceResponse> placeList) {
        return MapPlaceResponseList.builder()
                .placeList(placeList)
                .build();
    }
}
