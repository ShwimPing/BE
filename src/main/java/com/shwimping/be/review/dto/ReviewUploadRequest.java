package com.shwimping.be.review.dto;

import com.shwimping.be.place.domain.Place;
import com.shwimping.be.review.domain.Review;
import com.shwimping.be.user.domain.User;
import java.math.BigDecimal;

public record ReviewUploadRequest(
        Long placeId,
        BigDecimal rating,
        String content
) {
    public Review toEntity(Place place, User user, String imageUrl) {
        return Review.builder()
                .place(place)
                .rating(rating)
                .content(content)
                .user(user)
                .reviewImageUrl(imageUrl)
                .build();
    }
}
