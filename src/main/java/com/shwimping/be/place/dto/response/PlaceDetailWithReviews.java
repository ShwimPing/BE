package com.shwimping.be.place.dto.response;

import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public record PlaceDetailWithReviews(
        Boolean hasNext,
        PlaceDetailResponse placeDetail,
        List<ReviewSimpleResponse> recentReviews
) {
}
