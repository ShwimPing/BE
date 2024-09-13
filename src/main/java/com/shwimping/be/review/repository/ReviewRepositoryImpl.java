package com.shwimping.be.review.repository;

import static com.shwimping.be.review.domain.QReview.review;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
                                review.content,
                                review.rating,
                                review.date,
                                review.reviewImageUrl
                        )
                )
                .from(review)
                .where(review.place.id.eq(placeId), review.id.lt(lastReviewId)) // lastReviewId를 기준으로 필터링
                .orderBy(review.id.desc())
                .limit(size) // 가져올 리뷰 수 제한
                .fetch();
    }

    @Override
    public Boolean hasNext(Long placeId, Long lastReviewId, Long size) {
        return jpaQueryFactory.selectOne()
                .from(review)
                .where(review.place.id.eq(placeId), review.id.lt(lastReviewId - size))
                .fetchFirst() != null;
    }
}
