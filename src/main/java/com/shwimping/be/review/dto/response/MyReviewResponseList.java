package com.shwimping.be.review.dto.response;

import java.util.List;

public record MyReviewResponseList(
        Boolean hasNext,
        List<MyReviewResponse> myReviewResponsesList
) {
    public static MyReviewResponseList of(Boolean hasNext, List<MyReviewResponse> myReviewResponsesList) {
        return new MyReviewResponseList(hasNext, myReviewResponsesList);
    }
}