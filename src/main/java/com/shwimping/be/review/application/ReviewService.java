package com.shwimping.be.review.application;

import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import com.shwimping.be.review.dto.response.ReviewSimpleResponseList;
import com.shwimping.be.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewSimpleResponseList getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size) {

        List<ReviewSimpleResponse> reviewSimpleResponse =
                reviewRepository.getReviewSimpleResponse(placeId, lastReviewId, size);

        Boolean hasNext = reviewRepository.hasNext(placeId, lastReviewId, size);

        return ReviewSimpleResponseList.of(hasNext, reviewSimpleResponse);
    }
}
