package com.shwimping.be.place.dto.response;

import com.shwimping.be.place.application.type.SortType;
import java.util.List;
import lombok.Builder;

@Builder
public record SearchPlaceResponseList(
        Long page,
        Boolean hasNext,
        Double longitude,
        Double latitude,
        Integer maxDistance,
        String keyWord,
        SortType sortType,
        List<SearchPlaceResponse> placeList
) {
    public static SearchPlaceResponseList of(
            Long page, Boolean hasNext, Double longitude, Double latitude, Integer maxDistance, String keyWord, SortType sortType, List<SearchPlaceResponse> placeList) {
        return SearchPlaceResponseList.builder()
                .page(page)
                .placeList(placeList)
                .longitude(longitude)
                .latitude(latitude)
                .hasNext(hasNext)
                .maxDistance(maxDistance)
                .keyWord(keyWord)
                .sortType(sortType)
                .build();
    }
}
