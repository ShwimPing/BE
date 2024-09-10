package com.shwimping.be.review.dto.response;

import java.util.List;

public record ReviewSimpleResponseList(
        Boolean hasNext,
        List<ReviewSimpleResponse> reviewSimpleResponseList
) {
    public static ReviewSimpleResponseList of(Boolean hasNext, List<ReviewSimpleResponse> reviewSimpleResponseList) {
        return new ReviewSimpleResponseList(hasNext, reviewSimpleResponseList);
    }
}
