package com.shwimping.be.place.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record SearchPlaceResponseList(
        Long page,
        List<SearchPlaceResponse> placeList
) {
    public static SearchPlaceResponseList of(Long page, List<SearchPlaceResponse> placeList) {
        return SearchPlaceResponseList.builder()
                .page(page)
                .placeList(placeList)
                .build();
    }
}
