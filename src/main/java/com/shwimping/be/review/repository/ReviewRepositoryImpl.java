package com.shwimping.be.review.repository;

import static com.shwimping.be.review.domain.QReview.review;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shwimping.be.review.dto.response.MyReviewResponse;
import com.shwimping.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReviewSimpleResponse> getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size) {
        return jpaQueryFactory.select(
                        Projections.constructor(ReviewSimpleResponse.class,
                                review.id,
                                review.user.nickname,
                                review.user.profileImageUrl,
                                review.content,
                                review.rating,
                                review.date,
                                review.reviewImageUrl
                        )
                )
                .from(review)
                .where(review.place.id.eq(placeId), review.id.lt(lastReviewId)) // lastReviewId를 기준으로 필터링
                .orderBy(review.id.desc())
                .limit(size + 1) // 가져올 리뷰 수 제한
                .fetch();
    }

    @Override
    public List<MyReviewResponse> getMyFirstReview(Long userId, Long size) {
        return jpaQueryFactory.select(Projections.constructor(MyReviewResponse.class,
                        review.id,
                        review.place.category,
                        review.place.name,
                        review.content,
                        review.rating,
                        review.date,
                        review.reviewImageUrl
                ))
                .from(review)
                .where(review.user.id.eq(userId))
                .orderBy(review.id.desc())
                .limit(size + 1)
                .fetch();
    }

    @Override
    public List<MyReviewResponse> getMyReview(Long userId, Long lastReviewId, Long size) {
        return jpaQueryFactory.select(
                        Projections.constructor(MyReviewResponse.class,
                                review.id,
                                review.place.category,
                                review.place.name,
                                review.content,
                                review.rating,
                                review.date,
                                review.reviewImageUrl
                        )
                )
                .from(review)
                .where(review.user.id.eq(userId), review.id.lt(lastReviewId)) // lastReviewId를 기준으로 필터링
                .orderBy(review.id.desc())
                .limit(size + 1) // 가져올 리뷰 수 제한
                .fetch();
    }
}
