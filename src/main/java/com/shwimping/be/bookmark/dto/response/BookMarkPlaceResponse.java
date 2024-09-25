package com.shwimping.be.bookmark.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shwimping.be.place.domain.type.Category;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record BookMarkPlaceResponse(
        Long placeId,
        String name,
        String address,
        Category category,
        @JsonFormat(pattern = "HH:mm")
        LocalTime openTime,
        @JsonFormat(pattern = "HH:mm")
        LocalTime closeTime,
        Double rating,
        Long reviewCount
) {
    @JsonProperty("rating")
    public Double getFormattedRating() {
        return Math.round(rating * 10) / 10.0;
    }
}
