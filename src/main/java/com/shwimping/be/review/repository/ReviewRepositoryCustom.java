package com.shwimping.be.review.repository;

import com.shwimping.be.review.dto.response.MyReviewResponse;
import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewSimpleResponse> getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size);

    List<MyReviewResponse> getMyFirstReview(Long userId);

    List<MyReviewResponse> getMyReview(Long userId, Long lastReviewId, Long size);

    Boolean hasNextMyReview(Long userId, Long lastReviewId, Long size);
}
