package com.shwimping.be.place.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.shwimping.be.place.domain.type.Category;
import java.time.LocalTime;

public record SearchPlaceResponse(
        Long placeId,
        String name,
        String address,
        Long distance,
        Category category,
        @JsonFormat(pattern = "HH:mm")
        LocalTime openTime,
        @JsonFormat(pattern = "HH:mm")
        LocalTime closeTime,
        Double rating
) {
    @JsonProperty("rating")
    public Double getFormattedRating() {
        return Math.round(rating * 10) / 10.0;
    }
}
