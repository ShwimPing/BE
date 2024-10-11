package com.shwimping.be.review.application;

import com.amazonaws.util.StringUtils;
import com.shwimping.be.global.application.NCPStorageService;
import com.shwimping.be.place.application.PlaceService;
import com.shwimping.be.place.domain.Place;
import com.shwimping.be.review.dto.request.ReviewUploadRequest;
import com.shwimping.be.review.dto.response.MyReviewResponse;
import com.shwimping.be.review.dto.response.MyReviewResponseList;
import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import com.shwimping.be.review.dto.response.ReviewSimpleResponseList;
import com.shwimping.be.review.exception.CanNotDeleteReviewException;
import com.shwimping.be.review.exception.ReviewNotFoundException;
import com.shwimping.be.review.exception.errorcode.ReviewErrorCode;
import com.shwimping.be.review.repository.ReviewRepository;
import com.shwimping.be.user.application.UserService;
import com.shwimping.be.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final PlaceService placeService;
    private final NCPStorageService ncpStorageService;

    public ReviewSimpleResponseList getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size) {

        List<ReviewSimpleResponse> reviewSimpleResponseList =
                reviewRepository.getReviewSimpleResponse(placeId, lastReviewId, size);

        boolean hasNext = reviewSimpleResponseList.size() == size + 1;

        // 마지막 원소를 제외한 서브 리스트 생성
        if (hasNext) {
            reviewSimpleResponseList = reviewSimpleResponseList.subList(0, reviewSimpleResponseList.size() - 1);
        }

        return ReviewSimpleResponseList.of(hasNext, reviewSimpleResponseList);
    }

    public MyReviewResponseList getMyReview(Long userId, Long lastReviewId, Long size) {

        List<MyReviewResponse> reviewSimpleResponse = (lastReviewId == 0)
                ? reviewRepository.getMyFirstReview(userId) : reviewRepository.getMyReview(userId, lastReviewId, size);

        Boolean hasNext = reviewRepository.hasNextMyReview(userId, lastReviewId, size);

        return MyReviewResponseList.of(hasNext, reviewSimpleResponse);
    }

    @Transactional
    public void uploadReview(Long userId, ReviewUploadRequest reviewUploadRequest, MultipartFile file) {

        String imageUrl = "";
        if (file != null) {
            imageUrl = ncpStorageService.uploadFile(file, "reviews");
        }

        User user = userService.getUserById(userId);
        Place place = placeService.getPlaceById(reviewUploadRequest.placeId());

        reviewRepository.save(reviewUploadRequest.toEntity(place, user, imageUrl));
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        reviewRepository.findById(reviewId)
                .ifPresentOrElse(
                        review -> {
                            if (review.getUser().getId().equals(userId)) {
                                if (StringUtils.hasValue(review.getReviewImageUrl())) {
                                    ncpStorageService.deleteFile(review.getReviewImageUrl());
                                }
                                reviewRepository.delete(review);
                            } else {
                                throw new CanNotDeleteReviewException(ReviewErrorCode.CANNOT_DELETE_REVIEW);
                            }
                        },
                        () -> {
                            throw new ReviewNotFoundException(ReviewErrorCode.REVIEW_NOT_FOUND);
                        }
                );
    }
}
