package com.shwimping.be.place.repository;

import static com.shwimping.be.place.domain.QPlace.place;
import static com.shwimping.be.review.domain.QReview.review;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shwimping.be.place.application.type.SortType;
import com.shwimping.be.place.domain.type.Category;
import com.shwimping.be.place.dto.response.SearchPlaceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public List<SearchPlaceResponse> findAllByLocationWithDistance(
            double longitude, double latitude, int maxDistance, List<Category> categoryList, SortType sortType,
            String keyword, long page) {

        // 동적 쿼리 실행 - 결과 리스트
        return jpaQueryFactory.select(
                        Projections.constructor(SearchPlaceResponse.class,
                                place.id,
                                place.name,
                                place.address,
                                distanceTemplate(longitude, latitude).as("distance"),
                                place.category,
                                place.openTime,
                                place.closeTime,
                                review.rating.avg().coalesce(0.0), // 평균 평점을 가져오고 null일 경우 0으로 대체
                                review.id.count().coalesce(0L) // 리뷰 수
                        )
                )
                .from(place)
                .leftJoin(place.reviewList, review)
                .where(distanceTemplate(longitude, latitude).loe(maxDistance), place.category.in(categoryList),
                        keywordSearchExpression(keyword))
                .groupBy(place.id)
                .orderBy(orderExpression(sortType, longitude, latitude), reviewCntOrder()) // 정렬 조건 추가
                .offset(page * 10) // 페이지 번호에 따라 결과를 10개씩 가져오도록 설정
                .limit(10)
                .fetch();
    }

    private OrderSpecifier<?> orderExpression(SortType sortType, double longitude, double latitude) {
        return switch (sortType) {
            case STAR_DESC -> review.rating.avg().coalesce(0.0).desc();
            case DISTANCE_ASC -> distanceTemplate(longitude, latitude).asc(); // 거리순으로 정렬
        };
    }

    // 리뷰 수로 정렬 - 다른 정렬 조건으로 정렬한 후에
    private OrderSpecifier<?> reviewCntOrder() {
        return review.id.count().coalesce(0L).desc();
    }

    private NumberTemplate<Long> distanceTemplate(double longitude, double latitude) {
        return Expressions.numberTemplate(Long.class,
                "ST_Distance_Sphere(point({0}, {1}), point(place.longitude, place.latitude))", longitude, latitude);
    }

    private BooleanExpression keywordSearchExpression(String keyword) {
        if (StringUtils.hasText(keyword)) {
            return place.name.contains(keyword).or(place.address.contains(keyword));
        } else {
            return null;
        }
    }

    public Long countByLocationWithDistance(
            double longitude, double latitude, int maxDistant, List<Category> categoryList) {

        return jpaQueryFactory.select(place.count())
                .from(place)
                .where(distanceTemplate(longitude, latitude).loe(maxDistant), place.category.in(categoryList))
                .fetchOne();
    }
}
