package com.shwimping.be.review.repository;

import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewSimpleResponse> getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size);

    Boolean hasNext(Long placeId, Long lastReviewId, Long size);
}
